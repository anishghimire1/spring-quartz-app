package com.anish.job.demo.controller;

import com.anish.job.demo.model.UserProvidedDetails;
import com.anish.job.demo.service.SchedulerService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuartzJobControllerTest {

    private static final String JOB_ENDPOINT = "/job";

    private final String JSON = "application/json;charset=UTF-8";
    @Mock
    private SchedulerService schedulerService;

    @Mock
    private Scheduler scheduler;

    @InjectMocks
    private QuartzJobController controller;

    @Before
    public void setup() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        when(schedulerService.getJobKeys()).thenReturn(getJobKeys());
        when(schedulerService.getScheduler()).thenReturn(scheduler);
    }

    @Test
    public void testGetRunningJobKeys() throws Exception {
        JsonPath response = given().when().get(JOB_ENDPOINT).
                then().
                statusCode(200).extract().response().body().jsonPath();
        assertNotNull(response);
        List jobKeys = response.get();
        assertEquals(1, jobKeys.size());
    }

    @Test
    public void testRunJob() throws Exception{
        given().contentType(JSON).body(getUserProvidedDetails()).when().post(JOB_ENDPOINT).
                then().
                statusCode(200);
        verify(scheduler, times(1)).scheduleJob(any(JobDetail.class), any(Trigger.class));
        verify(scheduler, times(1)).start();
    }

    @Test
    public void testStopJob() throws Exception{
        given().contentType(JSON).body(getUserProvidedDetails()).when().delete(JOB_ENDPOINT).
                then().
                statusCode(200);
        verify(scheduler, times(1)).deleteJob(any(JobKey.class));
    }

    private UserProvidedDetails getUserProvidedDetails(){
        UserProvidedDetails details = new UserProvidedDetails();
        details.setDetails("user details");
        details.setInterval(5);
        details.setJobName("quartzJob");
        return details;
    }

    private List<JobKey> getJobKeys(){
        JobKey jobKey = new JobKey("name", "group");
        return Collections.singletonList(jobKey);
    }
}
