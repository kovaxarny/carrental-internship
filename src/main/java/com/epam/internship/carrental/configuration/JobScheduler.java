package com.epam.internship.carrental.configuration;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobScheduler{

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "exportCarsJob")
    private Job job;

//    @Scheduled(cron = "*/5 * * * * *") //for testing purposes
    @Scheduled(cron = "0 0 0 * * *")
    public void jobScheduler(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("Time",System.currentTimeMillis()).toJobParameters();

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
