package com.boreksolutions.hiresenseapi.config.exceptions.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ExceptionResponseObject {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String path;
    private HttpStatus status;
    private String message;
    private List<ValidationError> validationErrors;

    public ExceptionResponseObject() {
        timestamp = LocalDateTime.now();
    }

    public ExceptionResponseObject(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ExceptionResponseObject(HttpStatus status, String message, List<ValidationError> validationErrors) {
        this();
        this.status = status;
        this.message = message;
        this.validationErrors = validationErrors;
    }

}
