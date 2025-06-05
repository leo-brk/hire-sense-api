package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.company.Company;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.job.dto.request.CreateJob;
import com.boreksolutions.hiresenseapi.core.job.dto.response.JobDto;
import com.boreksolutions.hiresenseapi.core.job.dto.response.ViewJob;
import org.mapstruct.*;
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

    @Mapping(target = "updatedById", source = "updatedBy.id")
    @Mapping(target = "industry", source = "industry", qualifiedByName = "mapIndustry")
    @Mapping(target = "company", source = "company", qualifiedByName = "mapCompany")
    @Mapping(target = "city", source = "city", qualifiedByName = "mapCity")
    ViewJob jobEntityToViewJob(JobEntity jobEntity);

    @Named("mapIndustry")
    static String mapIndustry(Industry industry) {
        return industry != null ? industry.getName() : null;
    }

    @Named("mapCompany")
    static String mapCompany(Company company) {
        return company != null ? company.getName() : null;
    }

    @Named("mapCity")
    static String mapCity(City city) {
        return city != null ? city.getName() : null;
    }

    @Mapping(target = "data", source = "content")
    @Mapping(target = "totalSize", expression = "java(page.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "size", expression = "java(page.getSize())")
    @Mapping(target = "page", expression = "java(page.getNumber())")
    PageObject<ViewJob> pageToPageObject(Page<JobEntity> page);
}