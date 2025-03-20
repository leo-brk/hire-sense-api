package com.boreksolutions.hiresenseapi.config.validation.uniqueCountry;

import com.boreksolutions.hiresenseapi.core.country.CountryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueCountryNameValidator implements ConstraintValidator<UniqueCountryName, String> {

    private final CountryRepository countryRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }
        return !countryRepository.existsByName(name);
    }
}