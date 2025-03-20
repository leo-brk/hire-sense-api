package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import jakarta.validation.Valid;

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
}