package com.anish.job.demo.job;

import com.anish.job.demo.service.JobService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class SimpleJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleJob.class);

    @Autowired
    private JobService jobService;

    public void execute(JobExecutionContext context) {
        String jobDetail = String.format("Job %s starting @ %s", context.getJobDetail().getKey().getName(), context.getFireTime());
        String userInput = (String) context.getJobDetail().getJobDataMap().get("userText");
        LOGGER.info(jobDetail);
        jobService.execute(jobDetail, userInput);
        LOGGER.info("Job {} completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
    }
}
