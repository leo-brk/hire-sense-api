package com.boreksolutions.hiresenseapi.config.batch.steps;

import com.boreksolutions.hiresenseapi.config.batch.FullFileDto;
import com.boreksolutions.hiresenseapi.config.batch.cache.BatchCacheService;
import com.boreksolutions.hiresenseapi.core.company.Company;
import com.boreksolutions.hiresenseapi.core.company.CompanyRepository;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Optional;

import static com.boreksolutions.hiresenseapi.config.batch.steps.CommonLogic.convertStringToInteger;
import static com.boreksolutions.hiresenseapi.config.batch.steps.CommonLogic.fixItemName;

@Configuration
@RequiredArgsConstructor
public class CompanyStepConfig {

    private final CompanyRepository companyRepository;
    private final BatchCacheService batchCacheService;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public ItemProcessor<FullFileDto, Company> companyItemProcessor() {
        return item -> {

            String cleanedName = fixItemName(item.getCompanyName());

            if (item.getCompanyName() == null || item.getCompanyName().isBlank()
                    || batchCacheService.companyExists(cleanedName)) return null;

            Company company = new Company();
            company.setName(cleanedName);
            company.setCompanySize(item.getCompanySize());
            company.setOpenJobsNumber(convertStringToInteger(item.getOpenJobsCount()));

            Optional<Industry> industryOptional = batchCacheService.findIndustryByName(fixItemName(item.getIndustry()));
            industryOptional.ifPresent(company::setIndustry);

            batchCacheService.addCompany(company);

            return company;
        };
    }

    @Bean
    public ItemWriter<Company> companyItemWriter() {
        return companyRepository::saveAll;
    }

    @Bean
    public Step companyStep(ItemReader<FullFileDto> reader) {
        return new StepBuilder("companyStep", jobRepository)
                .<FullFileDto, Company>chunk(500, transactionManager)
                .reader(reader)
                .processor(companyItemProcessor())
                .writer(companyItemWriter())
                .build();
    }
}
