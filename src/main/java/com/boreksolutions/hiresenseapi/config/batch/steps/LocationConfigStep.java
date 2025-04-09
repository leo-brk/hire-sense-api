package com.boreksolutions.hiresenseapi.config.batch.steps;

import com.boreksolutions.hiresenseapi.config.batch.FullFileDto;
import com.boreksolutions.hiresenseapi.config.batch.cache.BatchCacheService;
import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.city.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.boreksolutions.hiresenseapi.config.batch.steps.CommonLogic.fixItemName;
import static com.boreksolutions.hiresenseapi.config.batch.steps.CommonLogic.getCity;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class LocationConfigStep {

    private final CityRepository cityRepository;
    private final BatchCacheService batchCacheService;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public ItemProcessor<FullFileDto, City> locationProcessor() {
        return item -> {
            String cleanedLocationName = fixItemName(item.getLocation());

            return getCity(item, cleanedLocationName, batchCacheService);
        };
    }

    @Bean
    public ItemWriter<City> locationItemWriter() {
        return item -> {
            log.info("Item length: {}", item.size());
            cityRepository.saveAll(item);
        };
    }

    @Bean
    public Step locationStep(ItemReader<FullFileDto> reader) {
        return new StepBuilder("cityStep", jobRepository)
                .<FullFileDto, City>chunk(500, transactionManager)
                .reader(reader)
                .processor(locationProcessor())
                .writer(locationItemWriter())
                .build();
    }
}