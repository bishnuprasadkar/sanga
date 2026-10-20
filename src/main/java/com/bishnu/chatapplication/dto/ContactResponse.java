package com.bishnu.chatapplication.dto;

import java.time.Instant;

public record ContactResponse(Long id, UserSummaryResponse user, Instant createdAt) {}
