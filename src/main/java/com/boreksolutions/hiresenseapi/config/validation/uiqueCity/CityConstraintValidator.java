package com.boreksolutions.hiresenseapi.config.validation.uiqueCity;


import com.boreksolutions.hiresenseapi.core.city.CityRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class CityConstraintValidator implements ConstraintValidator<UniqueCity, String> {

    private final CityRepository cityRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !cityRepository.existsByName(name);
    }
}
