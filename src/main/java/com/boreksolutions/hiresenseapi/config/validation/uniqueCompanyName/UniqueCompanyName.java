package com.boreksolutions.hiresenseapi.config.validation.uniqueCompanyName;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueCompanyNameValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCompanyName {
    String message() default "Company name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}