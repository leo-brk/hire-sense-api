package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CreateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.response.CompanyDto;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Company dtoToEntity(CreateCompany createCompany);

    @Mapping(target = "data", source = "content")
    @Mapping(target = "totalSize", expression = "java(page.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "size", expression = "java(page.getSize())")
    @Mapping(target = "page", expression = "java(page.getNumber())")
    PageObject<CompanyDto> pageToPageObject(Page<Company> page);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateDtoToEntity(CompanyDto source, @MappingTarget Company target);
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    CompanyDto entityToDto(Company company);
}