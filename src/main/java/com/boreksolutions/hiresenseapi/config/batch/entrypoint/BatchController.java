package com.boreksolutions.hiresenseapi.config.batch.entrypoint;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

    private final CsvUploadService csvUploadService;

    @PostMapping("/start")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        csvUploadService.receiveFile(file);
        return ResponseEntity.ok("File received successfully - Starting batch process...");
    }

    //For dev. purposes
    @DeleteMapping("/clear-data")
    public ResponseEntity<String> clearData() {
        csvUploadService.clearData();
        return ResponseEntity.ok("Data cleared successfully");
    }
}