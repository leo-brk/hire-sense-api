package com.boreksolutions.hiresenseapi.config.validation.uniqueCompanyName;

import com.boreksolutions.hiresenseapi.core.company.CompanyRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueCompanyNameValidator implements ConstraintValidator<UniqueCompanyName, String> {

    private final CompanyRepository companyRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true; // Allow null values (use @NotNull for null checks)
        }
        return !companyRepository.existsByName(name);
    }
}