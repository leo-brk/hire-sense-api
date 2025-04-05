package com.boreksolutions.hiresenseapi.config.batch.cache;

import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.city.CityRepository;
import com.boreksolutions.hiresenseapi.core.country.Country;
import com.boreksolutions.hiresenseapi.core.country.CountryRepository;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.industry.IndustryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatabaseCacheService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final IndustryRepository industryRepository;

    private List<City> cityCache;
    private List<Country> countryCache;
    private List<Industry> industryCache;

    @PostConstruct
    public void loadCache() {
        this.cityCache = cityRepository.findAll();
        this.countryCache = countryRepository.findAll();
        this.industryCache = industryRepository.findAll();
    }

    @PostConstruct
    public boolean countriesExistInDatabase(){
        return countryRepository.count() != 0;
    }

    @PostConstruct
    public boolean citiesExistInDatabase(){
        return cityRepository.count() != 0;
    }

    @PostConstruct
    public boolean industriesExistInDatabase(){
        return industryRepository.count() != 0;
    }

    public boolean databaseExistsInCache(){
        return countriesExistInDatabase()
                && citiesExistInDatabase()
                && industriesExistInDatabase();
    }

    public List<City> getAllCities() {
        return cityCache;
    }

    public List<Country> getAllCountries() {
        return countryCache;
    }

    public Optional<City> findCityByName(String name) {
        return cityCache.stream()
                .filter(city -> city.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Country> findCountryByName(String name) {
        return countryCache.stream()
                .filter(country -> country.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public boolean cityExists(String name) {
        return findCityByName(name).isPresent();
    }

    public boolean countryExists(String name) {
        return findCountryByName(name).isPresent();
    }
}
