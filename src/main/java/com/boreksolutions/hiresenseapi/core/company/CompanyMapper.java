package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CreateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.response.CompanyDto;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    /**
     * Maps a CreateCompany DTO to a Company entity.
     *
     * @param createCompany The CreateCompany DTO.
     * @return The mapped Company entity.
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Company dtoToEntity(CreateCompany createCompany);

    /**
     * Maps a Page of Company entities to a PageObject of CompanyDto.
     *
     * @param page The Page of Company entities.
     * @return The mapped PageObject of CompanyDto.
     */
    @Mapping(target = "data", source = "content")
    @Mapping(target = "totalSize", expression = "java(page.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "size", expression = "java(page.getSize())")
    @Mapping(target = "page", expression = "java(page.getNumber())")
    PageObject<CompanyDto> pageToPageObject(Page<Company> page);

    /**
     * Updates a Company entity with data from a CompanyDto.
     *
     * @param source The source CompanyDto.
     * @param target The target Company entity to update.
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateDtoToEntity(CompanyDto source, @MappingTarget Company target);

    /**
     * Maps a Company entity to a CompanyDto.
     *
     * @param company The Company entity.
     * @return The mapped CompanyDto.
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    CompanyDto entityToDto(Company company);
}