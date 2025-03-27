package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.job.dto.request.JobFilter;
import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody CreateJob createJob) {
        return new ResponseEntity<>(jobService.createJob(createJob), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

      @PostMapping("/filter")
      public ResponseEntity<Page<JobDto>> filterJobs(
            @RequestBody() JobFilter filter,
            Pageable pageable) {
      Page<JobDto> jobs = jobService.filterJobs(filter, pageable);
    return ResponseEntity.ok(jobs);
}

    @PutMapping("/{id}")
    public ResponseEntity<JobDto> updateJob(
            @PathVariable Long id, @Valid @RequestBody CreateJob updateJob) {
        return ResponseEntity.ok(jobService.updateJob(id, updateJob));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}