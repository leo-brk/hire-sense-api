package com.boreksolutions.hiresenseapi.config.batch.entrypoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Log4j2
@RequiredArgsConstructor
public class CsvUploadService {

    private final JobLauncher jobLauncher;
    private final Job job;

    public void receiveFile(MultipartFile file) {
        validateFile(file);
        saveFile(file);
        launchJob();
    }

    private void saveFile(MultipartFile file) {
        try {
            String uploadDir = new File("uploads").getAbsolutePath();
            File targetFile = new File(uploadDir, "data.csv");
            file.transferTo(targetFile);
            log.info("Uploaded file to: " + targetFile.getAbsolutePath());
            System.out.println("Saved to: " + targetFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty())
            throw new IllegalArgumentException("File is empty");

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv"))
            throw new IllegalArgumentException("Invalid file type, only CSV files are allowed");

        if (!"text/csv".equalsIgnoreCase(file.getContentType()))
            throw new IllegalArgumentException("Invalid content type, expected text/csv");
    }


    private void launchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("Failed to start CSV import job", e);
            throw new RuntimeException("Job launch failed", e);
        }
    }
}