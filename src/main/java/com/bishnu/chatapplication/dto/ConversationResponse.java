package com.bishnu.chatapplication.dto;

import com.bishnu.chatapplication.enums.ConversationType;
import java.time.Instant;

public record ConversationResponse(
    Long id,
    ConversationType type,
    String name,
    UserSummaryResponse createdBy,
    Instant createdAt,
    Instant updatedAt
) {}
