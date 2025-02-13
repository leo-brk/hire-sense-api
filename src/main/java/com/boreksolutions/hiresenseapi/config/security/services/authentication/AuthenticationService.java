package com.boreksolutions.hiresenseapi.config.security.services.authentication;


import com.boreksolutions.hiresenseapi.config.security.dto.JwtAuthenticationResponse;
import com.boreksolutions.hiresenseapi.config.security.dto.SignInRequest;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;

public interface AuthenticationService {

    JwtAuthenticationResponse signup(CreateUser request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}