package com.boreksolutions.hiresenseapi.config.validation.uniqueCountry;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = com.boreksolutions.hiresenseapi.config.validation.uniqueCountry.UniqueCountryNameValidator.class)
public @interface UniqueCountryName {
    String message() default "Country name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}