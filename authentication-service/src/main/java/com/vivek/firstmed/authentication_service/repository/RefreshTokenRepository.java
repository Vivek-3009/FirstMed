package com.vivek.firstmed.authentication_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.firstmed.authentication_service.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
}
