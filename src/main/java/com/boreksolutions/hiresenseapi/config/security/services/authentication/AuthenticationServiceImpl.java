package com.boreksolutions.hiresenseapi.config.security.services.authentication;

import com.boreksolutions.hiresenseapi.config.security.dto.JwtAuthenticationResponse;
import com.boreksolutions.hiresenseapi.config.security.dto.SignInRequest;
import com.boreksolutions.hiresenseapi.config.security.services.database.JpaUserDetailsService;
import com.boreksolutions.hiresenseapi.config.security.services.jwt.JwtService;
import com.boreksolutions.hiresenseapi.config.security.user.SecurityUser;
import com.boreksolutions.hiresenseapi.core.user.User;
import com.boreksolutions.hiresenseapi.core.user.UserService;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JpaUserDetailsService jpaUserDetailsService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @Override
    public JwtAuthenticationResponse signup(CreateUser createUser) {
        User user = userService.createUser(createUser);

        var jwt = jwtService.generateToken(new SecurityUser(user));

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        var user = jpaUserDetailsService.loadUserByUsername(request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUser().getEmail(), request.getPassword()));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
