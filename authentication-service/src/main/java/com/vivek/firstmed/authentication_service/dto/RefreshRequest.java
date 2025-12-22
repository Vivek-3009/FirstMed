package com.vivek.firstmed.authentication_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshRequest {
    String RefreshToken;
}