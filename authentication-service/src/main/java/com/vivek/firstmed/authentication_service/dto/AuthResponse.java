package com.vivek.firstmed.authentication_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    String accessToken;
    String refreshToken;
    long expiresInSeconds;
}
