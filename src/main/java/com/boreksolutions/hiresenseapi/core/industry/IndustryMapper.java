package com.boreksolutions.hiresenseapi.core.industry;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IndustryMapper {

    @Mapping(target = "data", source = "content")
    @Mapping(target = "totalSize", expression = "java(page.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "size", expression = "java(page.getSize())")
    @Mapping(target = "page", expression = "java(page.getNumber())")
    PageObject<IndustryDto> pageToPageObject(Page<Industry> page);

    List<IndustryDto> entityListToDtoList(List<Industry> industryList);

    IndustryDto entityToDto(Industry industry);
}