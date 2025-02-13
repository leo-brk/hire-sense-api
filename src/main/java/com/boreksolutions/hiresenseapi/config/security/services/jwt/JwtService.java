package com.boreksolutions.hiresenseapi.config.security.services.jwt;

import com.boreksolutions.hiresenseapi.config.security.user.SecurityUser;

public interface JwtService {

    String extractUserName(String token);

    String generateToken(SecurityUser userDetails);

    String extractTenant(String token);
}
