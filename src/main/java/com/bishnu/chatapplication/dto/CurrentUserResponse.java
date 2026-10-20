package com.bishnu.chatapplication.dto;

import com.bishnu.chatapplication.enums.OnlineStatus;
import java.time.Instant;

public record CurrentUserResponse(
    Long id,
    String username,
    String displayName,
    String profilePicture,
    OnlineStatus onlineStatus,
    Instant lastSeen
) {}
