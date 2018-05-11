package com.epam.internship.carrental.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

/**
 * The class schedules all Jobs.
 */
@Component

//Remove these PropertySources before creating JAR
@PropertySource("classpath:bar.properties")
@PropertySource(value = "classpath:bar.properties", ignoreResourceNotFound = true)

@EnableScheduling
public class JobScheduler {
    static final Logger LOGGER = LogManager.getLogger(JobScheduler.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "exportCarsJob")
    private Job exportCarsJob;

    @Autowired
    @Qualifier(value = "notifySubscribersJob")
    private Job notifySubscribersJob;

    @Value("${job.run}")
    private boolean isJobEnabled;

    @Scheduled(cron = "${job.schedule}")
    public void runJobsOnCondition() {
        if (isJobEnabled) {
            exportCarsJobScheduler();
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void runNotifySubscribersJob() {
        notifySubscribersJobScheduler();
    }

    public void exportCarsJobScheduler() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("Time", System.currentTimeMillis()).toJobParameters();

        try {
            jobLauncher.run(exportCarsJob, jobParameters);
        } catch (JobInstanceAlreadyCompleteException |
                JobExecutionAlreadyRunningException |
                JobParametersInvalidException |
                JobRestartException e) {
            LOGGER.error("There was a problem executing the exportCarsJob " + e);
        }
    }

    public void notifySubscribersJobScheduler() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("Time", System.currentTimeMillis()).toJobParameters();

        try {
            jobLauncher.run(notifySubscribersJob, jobParameters);
        } catch (JobInstanceAlreadyCompleteException |
                JobExecutionAlreadyRunningException |
                JobParametersInvalidException |
                JobRestartException e) {
            LOGGER.error("There was a problem executing the notifySubscribersJob " + e);
        }
    }
}
