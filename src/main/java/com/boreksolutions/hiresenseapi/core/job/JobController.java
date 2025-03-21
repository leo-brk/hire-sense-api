package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody CreateJob createJob) {
        JobDto jobDto = jobService.createJob(createJob);
        return new ResponseEntity<>(jobDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getJobById(@PathVariable Long id) {
        JobDto jobDto = jobService.getJobById(id);
        return new ResponseEntity<>(jobDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<JobDto>> getAllJobs() {
        List<JobDto> jobs = jobService.getAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobDto>> getJobsByCompany(@PathVariable Long companyId) {
        List<JobDto> jobs = jobService.getJobsByCompany(companyId);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<JobDto>> getJobsByCity(@PathVariable Long cityId) {
        List<JobDto> jobs = jobService.getJobsByCity(cityId);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/industry/{industryId}")
    public ResponseEntity<List<JobDto>> getJobsByIndustry(@PathVariable Long industryId) {
        List<JobDto> jobs = jobService.getJobsByIndustry(industryId);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<List<JobDto>> getJobsByTitle(@RequestParam String title) {
        List<JobDto> jobs = jobService.getJobsByTitle(title);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDto> updateJob(
            @PathVariable Long id, @Valid @RequestBody CreateJob updateJob) {
        JobDto jobDto = jobService.updateJob(id, updateJob);
        return new ResponseEntity<>(jobDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}