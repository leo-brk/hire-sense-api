package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.request.JobFilter;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    JobDto createJob(@Valid CreateJob createJob);

    JobDto getJobById(Long id);

    JobDto updateJob(Long id, CreateJob updateJob);

    void deleteJob(Long id);

    List<JobDto> getAllJobs();

    List<JobDto> getJobsByCompany(Long companyId);

    List<JobDto> getJobsByCity(Long cityId);

    List<JobDto> getJobsByIndustry(Long industryId);

    List<JobDto> getJobsByTitle(String title);

    Page<JobDto> filterJobs(JobFilter filter, Pageable pageable);

    PageObject<JobDto> filter(JobFilter jobFilter, Pageable pageable);
}