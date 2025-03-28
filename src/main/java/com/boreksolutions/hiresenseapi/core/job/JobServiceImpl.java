package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.config.exceptions.models.NotFoundException;
import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.city.CityRepository;
import com.boreksolutions.hiresenseapi.core.company.Company;
import com.boreksolutions.hiresenseapi.core.company.CompanyRepository;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.industry.IndustryRepository;
import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.request.JobFilter;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final IndustryRepository industryRepository;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    private final JobMapper jobMapper;
    private final JobCriteriaBuilder criteriaBuilder;

    @Override
    public Long createJob(CreateJob createJob) {
        Job job = jobMapper.toEntity(createJob);

        Industry industry = industryRepository.findById(createJob.getIndustryId())
                .orElseThrow(() -> new NotFoundException("Industry not found with ID: " + createJob.getIndustryId()));

        Company company = companyRepository.findById(createJob.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + createJob.getCompanyId()));

        City city = cityRepository.findById(createJob.getCityId())
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + createJob.getCityId()));
        job.setIndustry(industry);
        job.setCompany(company);
        job.setCity(city);
        return jobRepository.save(job).getId();
    }

    @Override
    public JobDto getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found with ID: " + id));
        return jobMapper.toDto(job);
    }

    @Override
    public PageObject<JobDto> filter(JobFilter filter, Pageable pageable) {
        Page<Job> page = criteriaBuilder.filterJobs(filter, pageable);
        return jobMapper.pageToPageObject(page);
    }

    @Override
    public JobDto updateJob(Long id, CreateJob updateJob) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found with ID: " + id));
        jobMapper.updateEntity(updateJob, job);

        if (updateJob.getIndustryId() != null) {
            Industry industry = industryRepository.findById(updateJob.getIndustryId())
                    .orElseThrow(() -> new NotFoundException("Industry not found with ID: " + updateJob.getIndustryId()));
            job.setIndustry(industry);
        }

        if (updateJob.getCompanyId() != null) {
            Company company = companyRepository.findById(updateJob.getCompanyId())
                    .orElseThrow(() -> new NotFoundException("Company not found with ID: " + updateJob.getCompanyId()));
            job.setCompany(company);
        }

        if (updateJob.getCityId() != null) {
            City city = cityRepository.findById(updateJob.getCityId())
                    .orElseThrow(() -> new NotFoundException("City not found with ID: " + updateJob.getCityId()));
            job.setCity(city);
        }

        Job updatedJob = jobRepository.save(job);
        return jobMapper.toDto(updatedJob);
    }

    @Override
    public void delete(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job with id " + id + "not found!"));
        job.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        jobRepository.save(job);
    }

}