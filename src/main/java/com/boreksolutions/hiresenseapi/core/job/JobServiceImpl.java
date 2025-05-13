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
import com.boreksolutions.hiresenseapi.core.job.dto.response.ViewJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    private final Job job;
    private List<Statistics> statistics;

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

    public Long count() {
        return jobEntityRepository.count();
    }

    @Override
    public List<Statistics> getStatistics(String filter) {
        return List.of();
    }

    @Override
    public JobDto getJobById(Long id) {
        JobEntity job = jobEntityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found with ID: " + id));
        return jobMapper.toDto(job);
    }

    @Override
    public PageObject<ViewJob> filter(JobFilter filter, Pageable pageable) {
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
    public List<Statistics> getJobDistributionStatistics(Boolean getLive) {
        if (getLive != null && getLive) return loadStatistics(null, null);
        return statistics;
    }

    @Override
    public List<Statistics> getJobDistributionStatistics(Boolean getLive, JobFilter filter) {
        return List.of();
    }


    @PostConstruct
    public void initStatistics() {
        this.statistics = Collections.unmodifiableList(loadStatistics(null, null));
    }

    @Override
    public List<Statistics> getJobDistributionStatistics(Boolean getLive, LocalDateTime from, LocalDateTime to) {
        if (getLive != null && getLive) {
            return loadStatistics(from, to);
        }
        return statistics;
    }

    private List<Statistics> loadStatistics(LocalDateTime from, LocalDateTime to) {
        List<Statistics> stats = new ArrayList<>();

        if (jobEntityRepository.count() <= 0) return stats;

        String[] keywords = {"Backend", "Frontend", "Devops", "IT", "Cloud", "CyberSecurity", "Manager", "UIUX", "Java", "RPA"};

        List<StatItem> jobItems = new ArrayList<>();
        for (String keyword : keywords) {
            Long count = (from != null && to != null)
                    ? jobEntityRepository.getJobsWithCrawledJobTitleName(keyword, from, to)
                    : jobEntityRepository.countByCrawledJobTitleContainingIgnoreCase(keyword);
            jobItems.add(new StatItem(keyword, count));
        }

        Statistics jobDistributionStatistics = new Statistics("jobDistributionStatistics");
        jobDistributionStatistics.setStatItems(jobItems);
        stats.add(jobDistributionStatistics);

        // City distribution
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> cityResults = jobEntityRepository.findTop3CitiesWithMostJobs(pageable);
        if (cityResults.size() < 3) throw new BadRequestException("Error getting city distribution statistics!");

        List<StatItem> cityItems = cityResults.stream()
                .map(result -> new StatItem((String) result[0], ((Long) result[1]).doubleValue()))
                .toList();

        Statistics cityStats = new Statistics("cityDistributionStatistics");
        cityStats.setStatItems(cityItems);
        stats.add(cityStats);

        // Company distribution
        List<Object[]> companyResults = jobEntityRepository.findCompaniesWithMostOpenJobs(pageable);
        if (companyResults.size() < 3) throw new BadRequestException("Error getting company distribution statistics!");

        List<StatItem> companyItems = companyResults.stream()
                .map(result -> new StatItem((String) result[0], ((Long) result[1]).doubleValue()))
                .toList();

        Statistics companyStats = new Statistics("companyDistributionStatistics");
        companyStats.setStatItems(companyItems);
        stats.add(companyStats);

        // Industry distribution
//        Pageable industryPageable = PageRequest.of(0, 10);
        List<Object[]> industryResults = jobEntityRepository.findIndustriesWithMostOpenJobs(PageRequest.of(0, 10));
        if (industryResults.isEmpty()) throw new BadRequestException("Error getting industry distribution statistics!");

        List<StatItem> industryItems = industryResults.stream()
                .map(result -> new StatItem((String) result[0], ((Long) result[1]).doubleValue()))
                .toList();

        Statistics industryStats = new Statistics("industryDistributionStatistics");
        industryStats.setStatItems(industryItems);
        stats.add(industryStats);

        // Position distribution
        List<Object[]> positionResults = jobEntityRepository.findTopPositions(pageable);
        if (positionResults.size() < 3) throw new BadRequestException("Error getting position distribution statistics!");

        List<StatItem> positionItems = positionResults.stream()
                .map(result -> new StatItem((String) result[0], ((Long) result[1]).doubleValue()))
                .toList();

        Statistics positionStats = new Statistics("positionDistributionStatistics");
        positionStats.setStatItems(positionItems);
        stats.add(positionStats);

        return stats;
    }

}