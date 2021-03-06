package com.epam.internship.carrental.configuration;

import com.epam.internship.carrental.service.user.User;
import com.epam.internship.carrental.service.user.UserRepository;
import com.epam.internship.carrental.service.car.Car;
import com.epam.internship.carrental.service.car.CarVO;
import com.epam.internship.carrental.service.search.Search;
import com.epam.internship.carrental.service.search.SearchServiceImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
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
import java.util.AbstractMap;
import java.util.List;
/**
 * Configuration class for a Job which exports user subscriptions with their search results to a CSV file.
 */
@Configuration
@EnableBatchProcessing
public class NotifySubscribersBatchConfig {
    @Autowired
    public SearchServiceImpl searchService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Search> reader() {
        JdbcCursorItemReader<Search> cursorItemReader = new JdbcCursorItemReader<>();
        cursorItemReader.setDataSource(dataSource);
        cursorItemReader.setSql("SELECT id,searched_make,searched_model,searched_car_type,searched_fuel_usage,searched_gearbox,searched_seats,user_id FROM db_car_test.search");
        cursorItemReader.setRowMapper(new SearchRowMapper());
        return cursorItemReader;
    }

    @Bean
    public SearchItemProcessor processor() {
        return new SearchItemProcessor();
    }


    @Bean
    public FlatFileItemWriter<AbstractMap.SimpleEntry<User, List<CarVO>>> writer() {
        FlatFileItemWriter<AbstractMap.SimpleEntry<User, List<CarVO>>> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("subscriptions.csv"));

        DelimitedLineAggregator<AbstractMap.SimpleEntry<User, List<CarVO>>> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Search, AbstractMap.SimpleEntry<User, List<CarVO>>>chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Qualifier("notifySubscribersJob")
    @Bean
    public Job notifySubscribersJob() {
        return jobBuilderFactory.get("notifySubscribersJob")
                .flow(step1())
                .end()
                .build();
    }

    private class SearchRowMapper implements RowMapper<Search> {
        @Override
        public Search mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            Search search = Search.builder()
                    .id(resultSet.getLong("id"))
                    .user(userRepository.findById(resultSet.getLong("user_id")).get())
                    .searchedMake(resultSet.getString("searched_make"))
                    .searchedModel(resultSet.getString("searched_model"))
                    .searchedFuelUsage(resultSet.getDouble("searched_fuel_usage"))
                    .searchedSeats(resultSet.getInt("searched_seats"))
                    .build();
            if (resultSet.getString("searched_gearbox") != null){
                search.setSearchedGearbox(Car.CarGearbox.valueOf(resultSet.getString("searched_gearbox")));
            }
            if (resultSet.getString("searched_car_type") != null){
                search.setSearchedCarType(Car.CarType.valueOf(resultSet.getString("searched_car_type")));
            }
            return search;
        }
    }

    private class SearchItemProcessor implements ItemProcessor<Search, AbstractMap.SimpleEntry<User, List<CarVO>>> {
        @Override
        public AbstractMap.SimpleEntry<User, List<CarVO>> process(Search search) throws Exception {
            return new AbstractMap.SimpleEntry<>(search.getUser(), searchService.searchCarsWithSpecList(search));
        }
    }
}
