package com.bishnu.chatapplication.dto;

import com.bishnu.chatapplication.enums.MessageStatus;
import java.time.Instant;

public record MessageReceiptResponse(
    Long recipientUserId,
    MessageStatus status,
    Instant deliveredAt,
    Instant readAt
) {}
