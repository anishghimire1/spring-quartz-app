package com.anish.job.demo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.quartz.JobKey;
import org.quartz.Scheduler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerServiceTest {
    @Mock
    private Scheduler scheduler;

    @InjectMocks
    private SchedulerService schedulerService;

    @Before
    public void setup() throws Exception{
        when(scheduler.getTriggerGroupNames()).thenReturn(Arrays.asList("trigger1", "trigger2"));
        Set<JobKey> jobKeySet = new HashSet<>();
        jobKeySet.add(new JobKey("jobName", "jobGroup"));
        when(scheduler.getJobKeys(any())).thenReturn(jobKeySet);
    }

    @Test
    public void testGetScheduler() {
        assertNotNull(schedulerService.getScheduler());
    }

    @Test
    public void testGetJobKeys() throws Exception {
        List<JobKey> jobKeys = schedulerService.getJobKeys();
        assertNotNull(jobKeys);
        assertEquals("jobName", jobKeys.get(0).getName());
        assertEquals("jobGroup", jobKeys.get(0).getGroup());
    }
}
