package com.bishnu.chatapplication.dto;

import java.time.Instant;
import java.util.List;

public record MessageResponse(
    Long id,
    Long conversationId,
    UserSummaryResponse sender,
    String content,
    Boolean edited,
    Boolean deleted,
    Long replyToMessageId,
    Instant createdAt,
    Instant updatedAt,
    List<MessageReceiptResponse> receipts
) {}
