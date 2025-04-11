package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.config.exceptions.models.BadRequestException;
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
import com.boreksolutions.hiresenseapi.core.job.dto.response.StatItem;
import com.boreksolutions.hiresenseapi.core.job.dto.response.Statistics;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobEntityRepository jobEntityRepository;
    private final IndustryRepository industryRepository;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    private final JobMapper jobMapper;
    private final JobCriteriaBuilder criteriaBuilder;

    @Override
    public Long createJob(CreateJob createJob) {
        JobEntity job = jobMapper.toEntity(createJob);

        Industry industry = industryRepository.findById(createJob.getIndustryId())
                .orElseThrow(() -> new NotFoundException("Industry not found with ID: " + createJob.getIndustryId()));

        Company company = companyRepository.findById(createJob.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + createJob.getCompanyId()));

        City city = cityRepository.findById(createJob.getCityId())
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + createJob.getCityId()));
        job.setIndustry(industry);
        job.setCompany(company);
        job.setCity(city);
        return jobEntityRepository.save(job).getId();
    }

    @Override
    public JobDto getJobById(Long id) {
        JobEntity job = jobEntityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found with ID: " + id));
        return jobMapper.toDto(job);
    }

    @Override
    public PageObject<JobDto> filter(JobFilter filter, Pageable pageable) {
        Page<JobEntity> page = criteriaBuilder.filterJobs(filter, pageable);
        return jobMapper.pageToPageObject(page);
    }

    @Override
    public JobDto updateJob(Long id, CreateJob updateJob) {
        JobEntity job = jobEntityRepository.findById(id)
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

        JobEntity updatedJob = jobEntityRepository.save(job);
        return jobMapper.toDto(updatedJob);
    }

    @Override
    public void delete(Long id) {
        JobEntity job = jobEntityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job with id " + id + "not found!"));
        job.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        jobEntityRepository.save(job);
    }

    @Override
    public List<Statistics> getJobDistributionStatistics() {

        List<Statistics> statistics = new ArrayList<>();

        String [] keywords = {"backend", "frontend", "devops"};
        Long backend = jobEntityRepository.getJobsWithDescriptionName(keywords[0]);
        Long frontEnd = jobEntityRepository.getJobsWithDescriptionName(keywords[1]);
        Long devOps = jobEntityRepository.getJobsWithDescriptionName(keywords[2]);

        Statistics jobDistributionStatistics = new Statistics("jobDistributionStatistics");
        jobDistributionStatistics.setStatItems(List.of(new StatItem(keywords[0], backend), new StatItem(keywords[1], frontEnd), new StatItem(keywords[2], devOps)));
        statistics.add(jobDistributionStatistics);

        //Get city distribution
        Pageable pageable = PageRequest.of(0, 3);  // First page, limit 3 records
        List<Object[]> results = jobEntityRepository.findTop3CitiesWithMostJobs(pageable);

        if (results.isEmpty() || results.size() < 3) throw new BadRequestException("Error getting city distribution statistics!");

        Statistics cityDistributionStatistics = new Statistics("cityDistributionStatistics");
        List<StatItem> cityDistributions = results.stream()
                .map(result -> new StatItem((String) result[0], ((Long) result[1]).doubleValue())).toList();
        cityDistributionStatistics.setStatItems(cityDistributions);
        statistics.add(cityDistributionStatistics);

        //Get company distribution
        Pageable Companypageable = PageRequest.of(0, 5);  // First page, limit 5 records
        List<Object[]> Copanyresults = jobEntityRepository.findCompaniesWithMostOpenJobs(Companypageable);

        if (Copanyresults.isEmpty() || Copanyresults.size() < 5) throw new BadRequestException("Error getting company distribution statistics!");

        Statistics companyDistributionStatistics = new Statistics("companyDistributionStatistics");
        List<StatItem> companyDistributions = Copanyresults.stream()
                .map(result -> new StatItem((String) result[0], ((Long) result[1]).doubleValue())).toList();
        companyDistributionStatistics.setStatItems(companyDistributions);
        statistics.add(companyDistributionStatistics);

        return statistics;
    }
}