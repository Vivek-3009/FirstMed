package com.vivek.firstmed.authentication_service.service;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vivek.firstmed.authentication_service.jwt.JwtUtil;
import com.vivek.firstmed.authentication_service.repository.RefreshTokenRepository;
import com.vivek.firstmed.authentication_service.repository.RoleRepository;
import com.vivek.firstmed.authentication_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final RefreshTokenRepository refreshRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Environment env;
    
}
