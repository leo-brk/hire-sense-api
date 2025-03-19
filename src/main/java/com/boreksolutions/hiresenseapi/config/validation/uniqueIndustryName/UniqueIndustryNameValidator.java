package com.boreksolutions.hiresenseapi.config.validation.uniqueIndustryName;

import com.boreksolutions.hiresenseapi.core.industry.IndustryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueIndustryNameValidator implements ConstraintValidator<UniqueIndustryName, String> {

    private final IndustryRepository industryRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true; // Allow null values (use @NotNull for null checks)
        }
        return !industryRepository.existsByName(name);
    }
}