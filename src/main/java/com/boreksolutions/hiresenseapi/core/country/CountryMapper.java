package com.boreksolutions.hiresenseapi.core.country;

import com.boreksolutions.hiresenseapi.core.country.dto.request.CreateCountry;
import com.boreksolutions.hiresenseapi.core.country.dto.response.CountryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper {
    CountryDto toDto(Country country);
    Country toEntity(CreateCountry createCountry);
    void updateEntity(CreateCountry updateCountry, @MappingTarget Country country);
    List<CountryDto> toDtoList(List<Country> countries);
}