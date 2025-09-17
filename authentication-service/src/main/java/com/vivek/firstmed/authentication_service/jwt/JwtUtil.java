package com.vivek.firstmed.authentication_service.jwt;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final SecretKey key;

    public JwtUtil(org.springframework.core.env.Environment env) {
        String secret = env.getProperty("security.jwt.secret");
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT secret must be configured and at least 32 bytes");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
}
