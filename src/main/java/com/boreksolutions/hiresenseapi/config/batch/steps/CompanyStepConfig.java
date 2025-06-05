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
            int [] positionNumbers = getCompanySize(item.getCompanyName());
            company.setMinPositions(positionNumbers[0]);
            company.setMaxPositions(positionNumbers[1]);
            company.setOpenJobsNumber(item.getOpenJobsCount());
            Optional<Industry> industryOptional = batchCacheService.findIndustryByName(fixItemName(item.getIndustry()));
            industryOptional.ifPresent(company::setIndustry);

            batchCacheService.addCompany(company);

            return company;
        };
    }

    private int[] getCompanySize(String companySize) {
        if (companySize == null || companySize.trim().isEmpty()) {
            return new int[] {0, 0}; // or you can return null, depending on your preference
        }

        // Clean the input: keep only digits and dashes
        String cleaned = companySize.replaceAll("[^0-9\\-]", "").trim();

        if (cleaned.isEmpty()) {
            return new int[] {0, 0};
        }

        // Check if it's a range (e.g., 100-200)
        if (cleaned.contains("-")) {
            String[] parts = cleaned.split("-");
            try {
                int min = Integer.parseInt(parts[0].trim());
                int max = (parts.length > 1 && !parts[1].isEmpty()) ? Integer.parseInt(parts[1].trim()) : min;
                return new int[] {min, max};
            } catch (NumberFormatException e) {
                return new int[] {0, 0}; // fallback if parsing fails
            }
        } else {
            try {
                int value = Integer.parseInt(cleaned);
                return new int[] {value, value};
            } catch (NumberFormatException e) {
                return new int[] {0, 0};
            }
        }
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