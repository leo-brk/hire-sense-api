package com.boreksolutions.hiresenseapi.core.industry;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto;
import com.boreksolutions.hiresenseapi.core.industry.dto.request.CreateIndustry;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface IndustryMapper {

    @Mapping(target = "id", ignore = true) // Ignore ID during creation
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Industry dtoToEntity(CreateIndustry createIndustry);

    @Mapping(target = "data", source = "content")
    @Mapping(target = "totalSize", expression = "java(page.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "size", expression = "java(page.getSize())")
    @Mapping(target = "page", expression = "java(page.getNumber())")
    PageObject<IndustryDto> pageToPageObject(Page<Industry> page);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateDtoToEntity(IndustryDto source, @MappingTarget Industry target);

    IndustryDto entityToDto(Industry industry);
}