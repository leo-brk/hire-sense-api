package com.boreksolutions.hiresenseapi.config.batch.steps;

import com.boreksolutions.hiresenseapi.config.batch.FullFileDto;
import com.boreksolutions.hiresenseapi.config.batch.cache.BatchCacheService;
import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.city.CityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

import static com.boreksolutions.hiresenseapi.config.batch.steps.CommonLogic.fixItemName;
import static com.boreksolutions.hiresenseapi.config.batch.steps.CommonLogic.getCity;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class CityStepConfig {

    private final CityRepository cityRepository;
    private final BatchCacheService batchCacheService;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManager entityManager;

    @Bean
    public ItemProcessor<FullFileDto, City> cityItemProcessor() {
        return item -> {
            String cleanedCityName = fixItemName(item.getCity());

            return getCity(item, cleanedCityName, batchCacheService);
        };
    }

    @Bean
    public ItemWriter<City> cityItemWriter() {
        return items -> {
            for (City city : items) {
                entityManager.persist(city);
            }
            entityManager.flush();
            entityManager.clear();
        };
    }

    @Bean
    @Qualifier("cityStep") // Qualifier is not necessary since the name of the bean is taken from the method name, this is simply for readability purposes.
    public Step cityStep(ItemReader<FullFileDto> reader) {
        return new StepBuilder("cityStep", jobRepository)
                .<FullFileDto, City>chunk(500, transactionManager)
                .reader(reader)
                .processor(cityItemProcessor())
                .writer(cityItemWriter())
                .build();
    }
}