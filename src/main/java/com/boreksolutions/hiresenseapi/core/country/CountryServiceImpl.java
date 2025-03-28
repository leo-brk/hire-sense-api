package com.boreksolutions.hiresenseapi.core.country;

import com.boreksolutions.hiresenseapi.config.exceptions.models.NotFoundException;
import com.boreksolutions.hiresenseapi.core.country.dto.request.CreateCountry;
import com.boreksolutions.hiresenseapi.core.country.dto.response.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public Long createCountry(CreateCountry createCountry) {
        Country country = countryMapper.toEntity(createCountry);
        return countryRepository.save(country).getId();
    }

    @Override
    public CountryDto getCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + id));
        return countryMapper.toDto(country);
    }

    @Override
    public List<CountryDto> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return countryMapper.toDtoList(countries);
    }

    @Override
    public CountryDto updateCountry(Long id, CreateCountry updateCountry) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + id));
        country.setName(updateCountry.getName());
        return countryMapper.toDto(countryRepository.save(country));
    }

    @Override
    public void deleteCountry(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + id));
        country.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        countryRepository.save(country);
    }
}