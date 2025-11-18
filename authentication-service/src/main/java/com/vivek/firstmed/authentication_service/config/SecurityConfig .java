package com.vivek.firstmed.authentication_service.config;

import org.springframework.context.annotation.Configuration;

import com.vivek.firstmed.authentication_service.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig  {

    private final JwtAuthenticationFilter jwtFilter;
    
}
