package com.anish.job.demo.controller;

import com.anish.job.demo.config.QuartzSpringConfig;
import com.anish.job.demo.job.SimpleJob;
import com.anish.job.demo.model.UserProvidedDetails;
import com.anish.job.demo.service.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Api(tags = "Quartz Job Scheduler")
@RestController
public class QuartzJobController {

    @Autowired
    private SchedulerService schedulerService;

    @ApiOperation(value = "Gets all running Quartz job keys")
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "ok")})
    @GetMapping("/job")
    @ResponseBody
    public ResponseEntity<List<JobKey>> getRunningJobKeys() throws SchedulerException {
        return new ResponseEntity<>(schedulerService.getJobKeys(), HttpStatus.OK);
    }

    @ApiOperation(value = "Run a Job passing some input")
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "ok"), @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred")})
    @PostMapping("/job")
    @ResponseBody
    public ResponseEntity<Object> runJob(@RequestBody UserProvidedDetails userProvidedDetails) throws SchedulerException {

        try{
            JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity(userProvidedDetails.getJobName() , "myGroup")
                    .usingJobData("userText", userProvidedDetails.getDetails())
                    .usingJobData("intervalInSeconds", userProvidedDetails.getInterval())
                    .build();

            Trigger trigger = QuartzSpringConfig.createTrigger(userProvidedDetails.getJobName()+"_trigger",
                    "myGroup", userProvidedDetails.getInterval());

            Scheduler scheduler = schedulerService.getScheduler();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();

            return ResponseEntity.ok().build();

        } catch (ObjectAlreadyExistsException exception){
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("detail", exception.getMessage());
            return ResponseEntity.badRequest().body(errorBody);
        }
    }

    @ApiOperation(value = "Stop a running Job")
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "ok"), @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred")})
    @DeleteMapping("/job")
    @ResponseBody
    public ResponseEntity<Object> stopJob(@RequestBody UserProvidedDetails userProvidedDetails) throws SchedulerException {

        try{
            JobKey jobKey = new JobKey(userProvidedDetails.getJobName(), "myGroup");
            Scheduler scheduler = schedulerService.getScheduler();
            scheduler.deleteJob(jobKey);
            return ResponseEntity.ok().build();

        } catch (Exception exception){
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("detail", exception.getMessage());
            return ResponseEntity.badRequest().body(errorBody);
        }
    }
}
