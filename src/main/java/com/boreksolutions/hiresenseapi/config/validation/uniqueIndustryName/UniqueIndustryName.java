package com.boreksolutions.hiresenseapi.config.validation.uniqueIndustryName;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueIndustryNameValidator.class)
public @interface UniqueIndustryName {
    String message() default "Industry name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}