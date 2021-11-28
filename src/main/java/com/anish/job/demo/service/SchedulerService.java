package com.anish.job.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

@Slf4j
@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public List<JobKey> getJobKeys() throws SchedulerException {
        List<JobKey> jobKeys = new ArrayList<>();

        for (String group : scheduler.getTriggerGroupNames()) {
            jobKeys.addAll(scheduler.getJobKeys(groupEquals(group)));
        }
        return jobKeys;
    }
}
