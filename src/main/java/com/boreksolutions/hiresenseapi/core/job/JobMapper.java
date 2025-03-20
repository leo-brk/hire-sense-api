package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper {

    @Mapping(target = "updatedById", source = "updatedBy.id")
    @Mapping(target = "industryId", source = "industry.id")
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "cityId", source = "city.id")
    JobDto toDto(Job job);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "industry", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "city", ignore = true)
    Job toEntity(CreateJob createJob);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "industry", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "city", ignore = true)
    void updateEntity(CreateJob updateJob, @MappingTarget Job job);
}