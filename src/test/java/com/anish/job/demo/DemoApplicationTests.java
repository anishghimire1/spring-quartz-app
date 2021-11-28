package com.anish.job.demo;

import com.anish.job.demo.controller.QuartzJobController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static junit.framework.TestCase.assertNotNull;

@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private QuartzJobController controller;

    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

}
