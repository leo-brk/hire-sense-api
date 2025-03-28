package com.boreksolutions.hiresenseapi.core.city;

import com.boreksolutions.hiresenseapi.config.exceptions.models.NotFoundException;
import com.boreksolutions.hiresenseapi.core.city.dto.request.CreateCity;
import com.boreksolutions.hiresenseapi.core.city.dto.response.CityDto;
import com.boreksolutions.hiresenseapi.core.country.Country;
import com.boreksolutions.hiresenseapi.core.country.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CityMapper cityMapper;

    @Override
    public Long createCity(CreateCity createCity) {
        City city = cityMapper.toEntity(createCity);
        Country country = countryRepository.findById(createCity.getCountryId())
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + createCity.getCountryId()));
        city.setCountry(country);
        return cityRepository.save(city).getId();
    }

    @Override
    public CityDto getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + id));
        return cityMapper.toDto(city);
    }

    //TODO use CityMapper
    @Override
    public List<CityDto> getAllCities() {
        return cityRepository.findAll().stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CityDto updateCity(Long id, CreateCity updateCity) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + id));
        cityMapper.updateEntity(updateCity, city);

        if (updateCity.getCountryId() != null) {
            Country country = countryRepository.findById(updateCity.getCountryId())
                    .orElseThrow(() -> new NotFoundException("Country not found with ID: " + updateCity.getCountryId()));
            city.setCountry(country);
        }

        City updatedCity = cityRepository.save(city);
        return cityMapper.toDto(updatedCity);
    }

    @Override
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + id));
        city.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        cityRepository.save(city);
    }
}