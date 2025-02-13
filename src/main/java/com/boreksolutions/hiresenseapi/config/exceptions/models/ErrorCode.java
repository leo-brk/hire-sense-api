package com.boreksolutions.hiresenseapi.config.exceptions.models;

import lombok.Getter;

@Getter
public enum ErrorCode {

    CITY_NOT_FOUND("city.not.found"),

    COMPANY_NOT_FOUND("company.not.found"),

    USER_NOT_FOUND("user.not.found");

    private final String reason;

    ErrorCode(String reason) {
        this.reason = reason;
    }
}
