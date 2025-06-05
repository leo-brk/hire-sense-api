package com.boreksolutions.hiresenseapi.config.batch.steps;

import com.boreksolutions.hiresenseapi.config.batch.FullFileDto;
import com.boreksolutions.hiresenseapi.config.batch.cache.BatchCacheService;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.industry.IndustryRepository;
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

import static com.boreksolutions.hiresenseapi.config.batch.steps.CommonLogic.fixItemName;

@Configuration
@RequiredArgsConstructor
public class IndustryStepConfig {

    private final IndustryRepository industryRepository;
    private final BatchCacheService batchCacheService;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public ItemProcessor<FullFileDto, Industry> industryItemProcessor() {
        return item -> {
            String cleanedName = fixItemName(item.getIndustry());

            if (cleanedName == null || batchCacheService.industryExists(cleanedName)) return null;
            //me bo industrin mos me shtu hiq asnjo kur upload se i shtojm manualisht
            Industry industry = new Industry();
            industry.setName(cleanedName);

            batchCacheService.addIndustry(industry);
            return industry;
        };
    }

    @Bean
    public ItemWriter<Industry> industryItemWriter() {
        return industryRepository::saveAll;
    }

    @Bean
    public Step industryStep(ItemReader<FullFileDto> reader) {
        return new StepBuilder("industryStep", jobRepository)
                .<FullFileDto, Industry>chunk(500, transactionManager)
                .reader(reader)
                .processor(industryItemProcessor())
                .writer(industryItemWriter())
                .build();
    }


}