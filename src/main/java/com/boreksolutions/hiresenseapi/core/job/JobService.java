package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.request.JobFilter;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface JobService {

    Long createJob(@Valid CreateJob createJob);

    JobDto getJobById(Long id);

    JobDto updateJob(Long id, CreateJob updateJob);

    void delete(Long id);

    PageObject<JobDto> filter(JobFilter jobFilter, Pageable pageable);
}