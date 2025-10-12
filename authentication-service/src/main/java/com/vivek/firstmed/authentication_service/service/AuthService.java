package com.vivek.firstmed.authentication_service.service;

import java.util.Set;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.firstmed.authentication_service.dto.AuthRequest;
import com.vivek.firstmed.authentication_service.dto.AuthResponse;
import com.vivek.firstmed.authentication_service.dto.RegisterRequest;
import com.vivek.firstmed.authentication_service.entity.Role;
import com.vivek.firstmed.authentication_service.entity.User;
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

    @Transactional
    public void register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (req.getEmail() != null && userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Role role = roleRepo.findByRoleName("ROLE_PATIENT")
                .orElseGet(() -> roleRepo.save(Role.builder().roleName("ROLE_PATIENT").build()));

        User user = User.builder()
                .userId("U" + System.currentTimeMillis())
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .enabled(true)
                .accountNonLocked(true)
                .roles(Set.of(role))
                .build();

        userRepo.save(user);
    }

    public AuthResponse login(AuthRequest req){}
    
}
