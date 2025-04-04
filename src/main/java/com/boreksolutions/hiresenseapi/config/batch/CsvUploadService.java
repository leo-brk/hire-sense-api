package com.boreksolutions.hiresenseapi.config.batch;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvUploadService {

    public void receiveFile(MultipartFile file) {
        validateFile(file);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Invalid file type, only CSV files are allowed");
        }

        if (!"text/csv".equalsIgnoreCase(file.getContentType())) {
            throw new IllegalArgumentException("Invalid content type, expected text/csv");
        }
    }
}