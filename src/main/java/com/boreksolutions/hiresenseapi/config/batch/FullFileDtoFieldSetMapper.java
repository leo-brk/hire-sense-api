package com.boreksolutions.hiresenseapi.config.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class FullFileDtoFieldSetMapper implements FieldSetMapper<FullFileDto> {

    @Override
    public FullFileDto mapFieldSet(FieldSet fieldSet) throws BindException {
        return new FullFileDto(
                fieldSet.readString("Unique Id"),
                fieldSet.readString("Crawl Timestamp"),
                fieldSet.readString("Pageurl"),
                fieldSet.readString("Post Date"),
                fieldSet.readString("Input Job Title"),
                fieldSet.readString("Crawled Job Title"),
                fieldSet.readString("Job Type"),
                fieldSet.readString("Job Description"),
                fieldSet.readString("Company Name"),
                fieldSet.readString("Location"),
                fieldSet.readString("City"),
                fieldSet.readString("Country"),
                fieldSet.readString("Industry"),
                fieldSet.readString("Company Size"),
                fieldSet.readString("Open Jobs Count"),
                fieldSet.readString("Open Jobs Count Url")
        );
    }
}