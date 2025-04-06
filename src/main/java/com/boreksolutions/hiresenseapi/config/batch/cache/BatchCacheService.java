package com.boreksolutions.hiresenseapi.config.batch.cache;

import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.city.CityRepository;
import com.boreksolutions.hiresenseapi.core.company.Company;
import com.boreksolutions.hiresenseapi.core.company.CompanyRepository;
import com.boreksolutions.hiresenseapi.core.country.Country;
import com.boreksolutions.hiresenseapi.core.country.CountryRepository;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.industry.IndustryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchCacheService {


    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final IndustryRepository industryRepository;
    private final CompanyRepository companyRepository;

    private List<City> cityCache;
    private List<Country> countryCache;
    private List<Industry> industryCache;
    private List<Company> companyCache;

    @PostConstruct
    public void loadCache() {
        this.cityCache = cityRepository.findAll();
        this.countryCache = countryRepository.findAll();
        this.industryCache = industryRepository.findAll();
        this.companyCache = companyRepository.findAll();
    }

    public Optional<City> findCityByName(String name) {
        return cityCache.stream()
                .filter(city -> city.getName() != null && city.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Country> findCountryByName(String name) {
        return countryCache.stream()
                .filter(country -> country.getName() != null && country.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Industry> findIndustryByName(String name) {
        return industryCache.stream()
                .filter(industry -> industry.getName() != null && industry.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Company> findCompanyByName(String name) {
        return companyCache.stream()
                .filter(company -> company.getName() != null && company.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public boolean cityExists(String name) {
        return findCityByName(name).isPresent();
    }

    public boolean countryExists(String name) {
        return findCountryByName(name).isPresent();
    }

    public boolean industryExists(String name) {
        return findIndustryByName(name).isPresent();
    }

    public boolean companyExists(String name) {
        return findCompanyByName(name).isPresent();
    }

    public void addCity(City city) {
        if (cityCache == null) cityCache = new ArrayList<>();
        cityCache.add(city);
    }

    public void addCountry(Country country) {
        if (countryCache == null) countryCache = new ArrayList<>();
        countryCache.add(country);
    }

    public void addIndustry(Industry industry) {
        if (industryCache == null) industryCache = new ArrayList<>();
        industryCache.add(industry);
    }

    public void addCompany(Company company) {
        if (companyCache == null) companyCache = new ArrayList<>();
        companyCache.add(company);
    }

    public void clearCache() {
        if (countryCache != null) countryCache.clear();
        if (cityCache != null) cityCache.clear();
        if (industryCache != null) industryCache.clear();
        if (companyCache != null) companyCache.clear();
    }
}
