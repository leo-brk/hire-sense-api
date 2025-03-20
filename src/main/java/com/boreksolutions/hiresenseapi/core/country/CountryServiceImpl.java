package com.boreksolutions.hiresenseapi.core.country;

import com.boreksolutions.hiresenseapi.core.country.dto.request.CreateCountry;
import com.boreksolutions.hiresenseapi.core.country.dto.response.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public CountryDto createCountry(CreateCountry createCountry) {
        Country country = countryMapper.toEntity(createCountry);
        Country savedCountry = countryRepository.save(country);
        return countryMapper.toDto(savedCountry);
    }

    @Override
    public CountryDto getCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with ID: " + id));
        return countryMapper.toDto(country);
    }

    @Override
    public CountryDto updateCountry(Long id, CreateCountry updateCountry) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with ID: " + id));
        countryMapper.updateEntity(updateCountry, country);
        Country updatedCountry = countryRepository.save(country);
        return countryMapper.toDto(updatedCountry);
    }

    @Override
    public void deleteCountry(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new RuntimeException("Country not found with ID: " + id);
        }
        countryRepository.deleteById(id);
    }
}