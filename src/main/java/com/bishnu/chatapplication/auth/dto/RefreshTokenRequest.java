package com.bishnu.chatapplication.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshTokenRequest(@NotBlank String refreshToken) {}
