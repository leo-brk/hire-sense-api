package com.boreksolutions.hiresenseapi.config.security.controller;

import com.boreksolutions.hiresenseapi.config.security.dto.JwtAuthenticationResponse;
import com.boreksolutions.hiresenseapi.config.security.dto.SignInRequest;
import com.boreksolutions.hiresenseapi.config.security.services.authentication.AuthenticationService;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid CreateUser createUser) {
        return ResponseEntity.ok(authenticationService.signup(createUser));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
