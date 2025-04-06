package com.boreksolutions.hiresenseapi.config.batch.steps;

import com.boreksolutions.hiresenseapi.config.batch.FullFileDto;
import com.boreksolutions.hiresenseapi.config.batch.cache.BatchCacheService;
import com.boreksolutions.hiresenseapi.core.country.Country;
import com.boreksolutions.hiresenseapi.core.country.CountryRepository;
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

@Configuration
@RequiredArgsConstructor
public class CountryStepConfig {

    private final CountryRepository countryRepository;
    private final BatchCacheService batchCacheService;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public ItemProcessor<FullFileDto, Country> countryItemProcessor() {
        return item -> {
            String countryName = item.getCountry();

            // If the country already exists in cache, skip it by returning null
            if (validateCountryName(countryName)) return null;

            Country country = new Country();
            country.setName(countryName.toUpperCase());
            batchCacheService.addCountry(country);
            return country;
        };
    }

    @Bean
    public ItemWriter<Country> countryItemWriter() {
        return countryRepository::saveAll;
    }

    @Bean
    @Qualifier("countryStep") // Optional, for readability
    public Step countryStep(ItemReader<FullFileDto> reader) {
        return new StepBuilder("countryStep", jobRepository)
                .<FullFileDto, Country>chunk(500, transactionManager)
                .reader(reader)
                .processor(countryItemProcessor())
                .writer(countryItemWriter())
                .build();
    }

    private boolean validateCountryName(String countryName) {
        return (countryName == null || countryName.isBlank() || batchCacheService.countryExists(countryName));
    }
}