package com.vivek.firstmed.authentication_service.service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.firstmed.authentication_service.dto.AuthRequest;
import com.vivek.firstmed.authentication_service.dto.AuthResponse;
import com.vivek.firstmed.authentication_service.dto.RefreshRequest;
import com.vivek.firstmed.authentication_service.dto.RegisterRequest;
import com.vivek.firstmed.authentication_service.entity.RefreshToken;
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

    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest req){
        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        long accessExp = Long.parseLong(env.getProperty("security.jwt.access-exp", "900"));
        long refreshExp = Long.parseLong(env.getProperty("security.jwt.refresh-exp", "2592000"));
        
         String accessToken = jwtUtil.generateToken(
                user.getUsername(),
                accessExp,
                Map.of("uid", user.getUserId(), "roles", user.getRoles().stream().map(Role::getRoleName).toList())
        );

        String refreshTokenStr = jwtUtil.generateToken(user.getUsername(), refreshExp, Map.of("type", "refresh"));

        RefreshToken rt = RefreshToken.builder()
                .token(refreshTokenStr)
                .expiryDate(Instant.now().plusSeconds(refreshExp))
                .user(user)
                .build();

        refreshRepo.save(rt);
        return new AuthResponse(accessToken, refreshTokenStr, accessExp);

    }

    @Transactional
    public AuthResponse refresh(RefreshRequest req) {
         Optional<RefreshToken> refeshToken = refreshRepo.findByToken(req.getRefreshToken());
    }
    
}
