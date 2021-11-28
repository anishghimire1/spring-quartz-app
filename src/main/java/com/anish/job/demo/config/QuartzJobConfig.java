package com.anish.job.demo.config;

import com.anish.job.demo.job.SimpleJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzJobConfig {

    private static final String CRON_EVERY_FIVE_MINUTES = "0 0/30 * ? * * *";

    @Bean(name = "fileWriterJob")
    public JobDetailFactoryBean runFileWriterJob() {
        return QuartzSpringConfig.createJobDetail(SimpleJob.class, "Simple File Writer Job");
    }

    // This job unless manually triggered from API runs every 30 minutes
    @Bean(name = "fileWriterCronTrigger")
    public CronTriggerFactoryBean triggerCronFileWriter(@Qualifier("fileWriterJob") JobDetail jobDetail) {
        return QuartzSpringConfig.createCronTrigger(jobDetail, CRON_EVERY_FIVE_MINUTES, "File Writer Cron Trigger");
    }
}
