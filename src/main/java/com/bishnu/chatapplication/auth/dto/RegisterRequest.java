package com.bishnu.chatapplication.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequest(
    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(
        regexp = "^([A-Za-z0-9_]+|[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,})$",
        message = "username may contain only letters, numbers, and underscores, or be a valid email address"
    )
    String username,
    @NotBlank
    @Size(min = 2, max = 100)
    @Pattern(
        regexp = "^\\p{L}+([ '\\-]\\p{L}+)*$",
        message = "display name may contain only letters, spaces, apostrophes, and hyphens"
    )
    String displayName,
    @NotBlank
    @Size(min = 8, max = 72)
    String password
) {}
