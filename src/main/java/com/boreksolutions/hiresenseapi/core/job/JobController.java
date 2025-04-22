package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.request.JobFilter;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import com.boreksolutions.hiresenseapi.core.job.dto.response.Statistics;
import com.boreksolutions.hiresenseapi.core.job.dto.response.ViewJob;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody CreateJob createJob) {
        return ResponseEntity.ok(jobService.createJob(createJob));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageObject<ViewJob>> filter(@RequestBody JobFilter filter, Pageable pageable) {
        return ResponseEntity.ok(jobService.filter(filter, pageable));
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<Statistics>> getJobDistributionStatistics(
            @RequestParam(required = false, name = "getLive") Boolean getLive) {
        return ResponseEntity.ok(jobService.getJobDistributionStatistics(getLive));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalJobCount() {
        return ResponseEntity.ok(jobService.getTotalJobCount());
    }


    @PutMapping("/{id}")
    public ResponseEntity<JobDto> update(
            @PathVariable Long id, @Valid @RequestBody CreateJob updateJob) {
        return ResponseEntity.ok(jobService.updateJob(id, updateJob));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobService.delete(id);
        return ResponseEntity.ok().build();
    }
}