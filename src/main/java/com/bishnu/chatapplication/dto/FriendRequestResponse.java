package com.bishnu.chatapplication.dto;

import com.bishnu.chatapplication.enums.FriendRequestStatus;
import java.time.Instant;

public record FriendRequestResponse(
    Long id,
    UserSummaryResponse sender,
    UserSummaryResponse receiver,
    FriendRequestStatus status,
    Instant createdAt
) {}
