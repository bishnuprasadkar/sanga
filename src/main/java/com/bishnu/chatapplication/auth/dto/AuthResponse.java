package com.bishnu.chatapplication.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn
) {}
