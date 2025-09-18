package com.vivek.firstmed.authentication_service.jwt;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
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

    public String generateToken(String subject, long ttlSeconds, Map<String, Object> claims) {
        Instant now = Instant.now();
        JwtBuilder b = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(ttlSeconds)));
        if (claims != null && !claims.isEmpty()) b.addClaims(claims);
        return b.signWith(key, SignatureAlgorithm.HS256).compact();
    }
    
}
