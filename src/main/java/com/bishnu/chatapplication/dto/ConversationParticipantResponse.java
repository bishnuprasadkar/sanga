package com.bishnu.chatapplication.dto;

import com.bishnu.chatapplication.enums.ConversationRole;
import java.time.Instant;

public record ConversationParticipantResponse(
    Long id,
    UserSummaryResponse user,
    ConversationRole role,
    Instant joinedAt
) {}
