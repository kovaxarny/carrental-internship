package com.epam.internship.carrental.configuration;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
        @PropertySource("classpath:bar.properties"),
        @PropertySource(value = "classpath:bar.properties", ignoreResourceNotFound = true)
})
//Remove these PropertySources before creating JAR
@EnableScheduling
public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "exportCarsJob")
    private Job job;

    @Value("${job.run}")
    private boolean isJobEnabled;

    @Scheduled(cron = "${job.schedule}")
    public void runJobsOnCondition() {
        if (isJobEnabled) {
            jobScheduler();
        }
    }

    public void jobScheduler() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("Time", System.currentTimeMillis()).toJobParameters();

        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        } catch (JobInstanceAlreadyCompleteException |
                JobExecutionAlreadyRunningException |
                JobParametersInvalidException |
                JobRestartException e) {
            e.printStackTrace();
        }
    }
}
