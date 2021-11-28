package com.anish.job.demo.service;

import com.anish.job.demo.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class JobService {

    public void execute(String jobDetail, String userInput) {
        try {
            FileUtil.writeContentToFile(jobDetail, userInput);
            log.info("Quartz Job run successful!");
        } catch (IOException exception) {
            log.error("Encountered error while writing quartz content to file!", exception);
        }
    }
}
