package com.anish.job.demo.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

public class FileUtil {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static void writeContentToFile(String jobDetail, String userText) throws IOException {
        // Make sure there is a folder to hold the transformed messages
        String relevantDir = System.getProperty("user.home") + FILE_SEPARATOR + "quartz";
        Files.createDirectories(Paths.get(relevantDir));

        // Create file using unique name
        String currentFile = new StringBuilder(relevantDir)
                .append(FILE_SEPARATOR)
                .append("Job_")
                .append(Instant.now().toEpochMilli())
                .append(".txt")
                .toString();

        Path outputFilePath = Paths.get(currentFile);
        String fileContent = new StringBuilder(jobDetail)
                .append(System.getProperty("line.separator"))
                .append(userText)
                .toString();
        Files.write(outputFilePath, fileContent.getBytes(StandardCharsets.UTF_8));
    }
}
