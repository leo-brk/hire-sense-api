package com.boreksolutions.hiresenseapi.core.city;

import com.boreksolutions.hiresenseapi.core.city.dto.request.CreateCity;
import com.boreksolutions.hiresenseapi.core.city.dto.response.CityDto;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityMapper {

    @Mapping(target = "countryId", source = "country.id")
    CityDto toDto(City city);

    @Mapping(target = "country", ignore = true)
    City toEntity(CreateCity createCity);

    @Mapping(target = "country", ignore = true)
    void updateEntity(CreateCity updateCity, @MappingTarget City city);

    List<CityDto> entityListToDtoList(List<City> cityList);

}