package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.city.CityRepository;
import com.boreksolutions.hiresenseapi.core.company.Company;
import com.boreksolutions.hiresenseapi.core.company.CompanyRepository;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.industry.IndustryRepository;
import com.boreksolutions.hiresenseapi.core.user.User;
import com.boreksolutions.hiresenseapi.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final IndustryRepository industryRepository;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    private final JobMapper jobMapper;

    @Override
    public JobDto createJob(CreateJob createJob) {
        Job job = jobMapper.toEntity(createJob);
        User updatedBy = userRepository.findById(createJob.getUpdatedById())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + createJob.getUpdatedById()));
        Industry industry = industryRepository.findById(createJob.getIndustryId())
                .orElseThrow(() -> new RuntimeException("Industry not found with ID: " + createJob.getIndustryId()));
        Company company = companyRepository.findById(createJob.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + createJob.getCompanyId()));
        City city = cityRepository.findById(createJob.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found with ID: " + createJob.getCityId()));
        job.setUpdatedBy(updatedBy);
        job.setIndustry(industry);
        job.setCompany(company);
        job.setCity(city);
        Job savedJob = jobRepository.save(job);
        return jobMapper.toDto(savedJob);
    }

    @Override
    public JobDto getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + id));
        return jobMapper.toDto(job);
    }

    @Override
    public JobDto updateJob(Long id, CreateJob updateJob) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + id));
        jobMapper.updateEntity(updateJob, job);
        if (updateJob.getUpdatedById() != null) {
            User updatedBy = userRepository.findById(updateJob.getUpdatedById())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + updateJob.getUpdatedById()));
            job.setUpdatedBy(updatedBy);
        }
        if (updateJob.getIndustryId() != null) {
            Industry industry = industryRepository.findById(updateJob.getIndustryId())
                    .orElseThrow(() -> new RuntimeException("Industry not found with ID: " + updateJob.getIndustryId()));
            job.setIndustry(industry);
        }
        if (updateJob.getCompanyId() != null) {
            Company company = companyRepository.findById(updateJob.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found with ID: " + updateJob.getCompanyId()));
            job.setCompany(company);
        }
        if (updateJob.getCityId() != null) {
            City city = cityRepository.findById(updateJob.getCityId())
                    .orElseThrow(() -> new RuntimeException("City not found with ID: " + updateJob.getCityId()));
            job.setCity(city);
        }
        Job updatedJob = jobRepository.save(job);
        return jobMapper.toDto(updatedJob);
    }

    @Override
    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found with ID: " + id);
        }
        jobRepository.deleteById(id);
    }

    @Override
    public List<JobDto> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(jobMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDto> getJobsByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId).stream()
                .map(jobMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDto> getJobsByCity(Long cityId) {
        return jobRepository.findByCityId(cityId).stream()
                .map(jobMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDto> getJobsByIndustry(Long industryId) {
        return jobRepository.findByIndustryId(industryId).stream()
                .map(jobMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDto> getJobsByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(jobMapper::toDto)
                .collect(Collectors.toList());
    }
}