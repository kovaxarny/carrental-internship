package com.epam.internship.carrental.configuration;

import com.epam.internship.carrental.car.Car;
import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Car> reader() {
        JdbcCursorItemReader<Car> cursorItemReader = new JdbcCursorItemReader<>();
        cursorItemReader.setDataSource(dataSource);
        cursorItemReader.setSql("SELECT id,make,model,car_type,fuel_usage,gearbox,seats FROM db_car_test.car");
        cursorItemReader.setRowMapper(new CarRowMapper<Car>());
        return cursorItemReader;
    }

    @Bean
    public CarItemProcessor processor() {
        return new CarItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<Car> writer() {
        FlatFileItemWriter<Car> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("cars.csv"));

        DelimitedLineAggregator<Car> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<Car> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"id", "make", "model", "carType", "seats", "fuelUsage", "gearbox"});

        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Car, Car>chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Qualifier("exportCarsJob")
    @Bean
    public Job exportCarsJob() {
        return jobBuilderFactory.get("exportCarsJob")
                .flow(step1())
                .end()
                .build();
    }

    private class CarRowMapper<T> implements RowMapper<Car> {
        @Override
        public Car mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            return Car.builder()
                    .id(resultSet.getLong("id"))
                    .make(resultSet.getString("make"))
                    .model(resultSet.getString("model"))
                    .carType(CarType.valueOf(resultSet.getString("car_type")))
                    .seats(resultSet.getInt("seats"))
                    .fuelUsage(resultSet.getDouble("fuel_usage"))
                    .gearbox(CarGearbox.valueOf(resultSet.getString("gearbox")))
                    .build();
        }
    }

    private class CarItemProcessor implements ItemProcessor<Car, Car> {
        @Override
        public Car process(Car car) throws Exception {
            return car;
        }
    }
}
