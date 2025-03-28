package com.boreksolutions.hiresenseapi.core.country;

import com.boreksolutions.hiresenseapi.core.country.dto.request.CreateCountry;
import com.boreksolutions.hiresenseapi.core.country.dto.response.CountryDto;
import java.util.*;

public interface CountryService {

    Long createCountry(CreateCountry createCountry);

    CountryDto getCountryById(Long id);

    CountryDto updateCountry(Long id, CreateCountry updateCountry);

    void deleteCountry(Long id);

    List<CountryDto> getAllCountries();
}