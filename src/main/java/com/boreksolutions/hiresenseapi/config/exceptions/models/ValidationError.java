package com.boreksolutions.hiresenseapi.config.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ValidationError {

    private String object;

    private String field;

    private Object rejectedValue;
    
    private String message;
}
