package com.boreksolutions.hiresenseapi.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
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

    @Value("file:uploads/data.csv")

    private Resource inputFeed;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public FlatFileItemReader<FullFileDto> csvFileReader(LineMapper<FullFileDto> lineMapper) {
        var itemReader = new FlatFileItemReader<FullFileDto>();
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
                "Industry",
                "Company Size",
                "Open Jobs Count",
                "Open Jobs Count Url");
        return tokenizer;
    }


    @Bean
    public ItemProcessor<FullFileDto, FullFileDto> processor() {
        return item -> item;
    }

    @Bean
    public ItemWriter<FullFileDto> writer() {
        return items -> {
            // No-op writer
        };
    }

    @Bean
    public Step importStep(ItemReader<FullFileDto> reader,
                           ItemProcessor<FullFileDto, FullFileDto> processor,
                           ItemWriter<FullFileDto> writer) {
        return new StepBuilder("importStep", jobRepository)
                .<FullFileDto, FullFileDto>chunk(1000, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importJob(Step importStep) {
        return new JobBuilder("importJob", jobRepository)
                .start(importStep)
                .listener(new JobCompletionNotificationListener())
                .build();
    }
}