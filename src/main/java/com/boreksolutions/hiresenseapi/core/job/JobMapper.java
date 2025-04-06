package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper {

    @Mapping(target = "industryId", source = "industry.id")
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "cityId", source = "city.id")
    JobDto toDto(JobEntity job);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "industry", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "city", ignore = true)
    JobEntity toEntity(CreateJob createJob);

    @Mapping(target = "industry", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "city", ignore = true)
    void updateEntity(CreateJob updateJob, @MappingTarget JobEntity job);

    @Mapping(target = "data", source = "content")
    @Mapping(target = "totalSize", expression = "java(page.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "size", expression = "java(page.getSize())")
    @Mapping(target = "page", expression = "java(page.getNumber())")
    PageObject<JobDto> pageToPageObject(Page<JobEntity> page);
}