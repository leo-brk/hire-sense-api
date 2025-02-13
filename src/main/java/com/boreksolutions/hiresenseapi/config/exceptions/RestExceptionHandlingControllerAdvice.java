package com.boreksolutions.hiresenseapi.config.exceptions;

import com.boreksolutions.hiresenseapi.config.exceptions.models.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


@ControllerAdvice
@Log4j2
public class RestExceptionHandlingControllerAdvice {

    private final HttpServletRequest request;
    private final MessageSource messageSource;

    public RestExceptionHandlingControllerAdvice(HttpServletRequest request,
                                                 MessageSource messageSource) {
        this.request = request;
        this.messageSource = messageSource;
    }


    private ResponseEntity<Object> buildResponseEntity(
            Exception exception, ExceptionResponseObject exceptionResponseObject) {

        exceptionResponseObject.setPath(request.getRequestURI());

        if (exception != null) {
            log.error(exception);
            log.error("Error Message: {}", exception.getMessage());
        }

        return new ResponseEntity<>(exceptionResponseObject, exceptionResponseObject.getStatus());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseObject> handleValidationErrors(MethodArgumentNotValidException exception,
                                                                          Locale locale) {
        String methodName = "handlerValidateException";

        log.error("{} -> Error fields: {}", methodName, exception.getCause(), exception);

        List<ValidationError> validationErrors = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {

            String object = error.getObjectName();
            String field = ((FieldError) error).getField();
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            String errorMessage;

            try {
                errorMessage = messageSource.getMessage(
                        (Objects.requireNonNull(error.getDefaultMessage())), null, locale);
            } catch (Exception e) {
                errorMessage = error.getDefaultMessage();
            }

            validationErrors.add(new ValidationError(object, field, rejectedValue, errorMessage));

            log.error("Validation Error:"
                            + " Path {},"
                            + " Object {},"
                            + " Field {}, "
                            + " Rejected Value {},"
                            + " Message: {}",
                    request.getRequestURI(), object, field, rejectedValue, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseObject(HttpStatus.BAD_REQUEST, "Validation Errors", validationErrors));
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.BAD_REQUEST, exception.getMessage(),
                        exception.getValidationErrors());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflict(ConflictException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.CONFLICT, exception.getMessage(), exception.getValidationErrors());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleEndpointNotFoundException(NoHandlerFoundException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.NOT_FOUND, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.NOT_FOUND, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerError(Exception exception) {
        if (exception instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotAllowedException(
            HttpRequestMethodNotSupportedException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "Data access error");

        return buildResponseEntity(exception, exceptionResponseObject);
    }

//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
//
//        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
//        String message = ex.getLocalizedMessage();
//
//        if (ex.getLocalizedMessage().equals("Full authentication is required to access this resource"))
//            message = "Missing Credentials!";
//
//
//        ExceptionResponseObject errorResponse = new ExceptionResponseObject(httpStatus,
//                "AUTHENTICATION FAILED: " + message);
//        return ResponseEntity.status(httpStatus).body(errorResponse);
//    }
//
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
//        ExceptionResponseObject exceptionResponseObject =
//                new ExceptionResponseObject(HttpStatus.FORBIDDEN, exception.getMessage());
//
//        return buildResponseEntity(exception, exceptionResponseObject);
//    }
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<Object> handleInvalidToken(ExpiredJwtException exception) {
//        ExceptionResponseObject exceptionResponseObject =
//                new ExceptionResponseObject(HttpStatus.FORBIDDEN, exception.getMessage());
//
//        return buildResponseEntity(exception, exceptionResponseObject);
//    }
//
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<Object> handleInvalidToken(MalformedJwtException exception) {
//        ExceptionResponseObject exceptionResponseObject =
//                new ExceptionResponseObject(HttpStatus.FORBIDDEN, exception.getMessage());
//
//        return buildResponseEntity(exception, exceptionResponseObject);
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Object> handleDataAccessException(HttpMessageNotReadableException exception) {
//
//        ExceptionResponseObject exceptionResponseObject =
//                new ExceptionResponseObject(HttpStatus.BAD_REQUEST, "Wrong parameters/body received");
//
//        return buildResponseEntity(exception, exceptionResponseObject);
//    }
//
//    @ExceptionHandler(FileIoException.class)
//    public ResponseEntity<Object> handleFileIoException(FileIoException exception) {
//
//        ExceptionResponseObject exceptionResponseObject =
//                new ExceptionResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
//
//        return buildResponseEntity(exception, exceptionResponseObject);
//    }
}