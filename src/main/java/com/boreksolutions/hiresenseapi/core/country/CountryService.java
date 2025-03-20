package com.boreksolutions.hiresenseapi.core.country;

import com.boreksolutions.hiresenseapi.core.country.dto.request.CreateCountry;
import com.boreksolutions.hiresenseapi.core.country.dto.response.CountryDto;

public interface CountryService {

    CountryDto createCountry(CreateCountry createCountry);

    CountryDto getCountryById(Long id);

    CountryDto updateCountry(Long id, CreateCountry updateCountry);

    void deleteCountry(Long id);
}