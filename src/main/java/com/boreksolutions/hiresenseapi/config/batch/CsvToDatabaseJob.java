package com.boreksolutions.hiresenseapi.config.batch;

import com.boreksolutions.hiresenseapi.config.batch.cache.BatchCacheService;
import com.boreksolutions.hiresenseapi.core.job.JobEntityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CsvToDatabaseJob {

    private static final Logger log = LoggerFactory.getLogger(CsvToDatabaseJob.class);
    @Value("file:uploads/data.csv")

    private Resource inputFeed;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JobEntityRepository jobEntityRepository;
    private final BatchCacheService cacheService;

    @Bean
    public FlatFileItemReader<FullFileDto> csvFileReader(LineMapper<FullFileDto> lineMapper) {
        var itemReader = new FlatFileItemReader<FullFileDto>();
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper);
        itemReader.setResource(inputFeed);
        return itemReader;
    }

    @Bean
    public DefaultLineMapper<FullFileDto> lineMapper(LineTokenizer tokenizer, FieldSetMapper<FullFileDto> mapper) {
        var lineMapper = new DefaultLineMapper<FullFileDto>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);
        return lineMapper;
    }

    @Bean
    public DelimitedLineTokenizer tokenizer() {
        var tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(";");
        tokenizer.setNames("Unique Id",
                "Crawl Timestamp",
                "Pageurl",
                "Post Date",
                "Input Job Title",
                "Crawled Job Title",
                "Job Type",
                "Job Description",
                "Company Name",
                "Location",
                "City",
                "Country",
                "Industry",
                "Company Size",
                "Open Jobs Count",
                "Open Jobs Count Url");
        tokenizer.setStrict(false);
        return tokenizer;
    }

    @Bean
    public Job job(Step countryStep, Step cityStep, Step locationStep, Step industryStep, Step companyStep, Step jobStep) {
        return new JobBuilder("saveJobs", jobRepository)
                .start(countryStep)
                .next(cityStep)
                //.next(industryStep)
                .next(companyStep)
                .next(jobStep)
                .listener(new JobCompletionNotificationListener())
                .build();
    }
}