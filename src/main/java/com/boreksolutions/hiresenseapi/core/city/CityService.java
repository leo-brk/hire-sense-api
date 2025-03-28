package com.boreksolutions.hiresenseapi.core.city;

import com.boreksolutions.hiresenseapi.core.city.dto.request.CreateCity;
import com.boreksolutions.hiresenseapi.core.city.dto.response.CityDto;
import java.util.List;

public interface CityService {

    Long createCity(CreateCity createCity);

    CityDto getCityById(Long id);

    CityDto updateCity(Long id, CreateCity updateCity);

    void deleteCity(Long id);

    List<CityDto> getAllCities();
}