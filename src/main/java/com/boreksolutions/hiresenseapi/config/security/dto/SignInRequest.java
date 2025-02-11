package com.boreksolutions.hiresenseapi.config.security.dto;

import lombok.Data;

@Data
public class SignInRequest {

    private String email;

    private String password;

}
