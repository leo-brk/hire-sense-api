package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.request.JobFilter;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import com.boreksolutions.hiresenseapi.core.job.dto.response.Statistics;
import com.boreksolutions.hiresenseapi.core.job.dto.response.ViewJob;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    Long createJob(@Valid CreateJob createJob);

    JobDto getJobById(Long id);

    JobDto updateJob(Long id, CreateJob updateJob);

    void delete(Long id);

    PageObject<ViewJob> filter(JobFilter jobFilter, Pageable pageable);

    List<Statistics> getJobDistributionStatistics(Boolean getLive);
}