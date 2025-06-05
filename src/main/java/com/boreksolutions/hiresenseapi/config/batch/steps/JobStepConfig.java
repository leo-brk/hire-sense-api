package com.boreksolutions.hiresenseapi.config.batch.steps;

import com.boreksolutions.hiresenseapi.config.batch.FullFileDto;
import com.boreksolutions.hiresenseapi.config.batch.cache.BatchCacheService;
import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.company.Company;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.job.JobEntity;
import com.boreksolutions.hiresenseapi.core.job.JobEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Optional;

import static com.boreksolutions.hiresenseapi.config.batch.steps.CommonLogic.convertStringToInteger;
import static com.boreksolutions.hiresenseapi.config.batch.steps.CommonLogic.fixItemName;

@Configuration
@RequiredArgsConstructor
public class JobStepConfig {

    private final JobEntityRepository jobEntityRepositoryRepository;
    private final BatchCacheService batchCacheService;
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;

    @Bean
    public ItemProcessor<FullFileDto, JobEntity> jobItemProcessor() {
        return item -> {
            // Clean and validate the job title
            String jobTitle = fixItemName(item.getInputJobTitle());
            String crawledJobTitle = fixItemName(item.getCrawledJobTitle());

            JobEntity jobEntity = new JobEntity();
            jobEntity.setTitle(jobTitle);
            jobEntity.setCrawledJobTitle(crawledJobTitle);
            jobEntity.setUrl(item.getPageUrl());
            jobEntity.setDescription(item.getJobDescription());
            jobEntity.setNumberOfSamePositions(convertStringToInteger(item.getOpenJobsCount()));
            //jobEntity.setJobType(item.getJobType());

            // Relating job to company, city, and industry using cached data
            Optional<Company> companyOptional = batchCacheService.findCompanyByName(fixItemName(item.getCompanyName()));
            companyOptional.ifPresent(jobEntity::setCompany);

            Optional<City> cityOptional = batchCacheService.findCityByName(fixItemName(item.getCity()));
            cityOptional.ifPresent(jobEntity::setCity);

            Optional<Industry> industryOptional = batchCacheService.findIndustryByName(fixItemName(item.getIndustry()));
            if (industryOptional.isPresent()) {
                jobEntity.setIndustry(industryOptional.get());
                return jobEntity;
            }
            else return null;
        };
    }

    @Bean
    public ItemWriter<JobEntity> jobItemWriter() {
        return jobEntityRepositoryRepository::saveAll;
    }


    @Bean
    public Step jobStep(ItemReader<FullFileDto> reader) {
        return new StepBuilder("jobStep", jobRepository)
                .<FullFileDto, JobEntity>chunk(500, transactionManager)
                .reader(reader)
                .processor(jobItemProcessor())
                .writer(jobItemWriter())
                .build();
    }
}

