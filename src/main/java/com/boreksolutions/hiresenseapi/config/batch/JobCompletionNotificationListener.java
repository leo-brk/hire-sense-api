package com.boreksolutions.hiresenseapi.config.batch;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.io.File;

@Log4j2
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final String FILE_PATH = "uploads/data.csv";

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job finished with status: {}", jobExecution.getStatus());
            //deleteUploadedFile();
        }
    }

    private void deleteUploadedFile() {
        File file = new File(FILE_PATH);
        if (file.exists() && file.delete()) {
            log.info("Uploaded file deleted successfully.");
        } else {
            log.warn("Failed to delete uploaded file.");
        }
    }
}