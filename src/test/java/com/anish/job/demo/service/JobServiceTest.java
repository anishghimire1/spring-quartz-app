package com.anish.job.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.anish.job.demo.util.FileUtil.FILE_SEPARATOR;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JobServiceTest {

    @InjectMocks
    private JobService jobService;

    @Test
    public void testExecute() throws Exception {
        // test that no exception is thrown and file is written
        jobService.execute("jobDetail", "userInput");

        String relevantDir = System.getProperty("user.home") + FILE_SEPARATOR + "quartz";

        List<File> files = Files.list(Paths.get(relevantDir))
                .map(Path::toFile)
                .collect(Collectors.toList());
        assertTrue(files.size() > 0);
        assertTrue(files.stream().anyMatch(file -> file.getName().contains("Job_")));
    }
}
