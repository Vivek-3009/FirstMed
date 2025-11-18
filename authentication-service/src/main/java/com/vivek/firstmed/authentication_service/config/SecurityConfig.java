package com.vivek.firstmed.authentication_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.vivek.firstmed.authentication_service.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig  {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{}
    
}
