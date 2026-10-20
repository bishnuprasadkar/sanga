package com.bishnu.chatapplication.dto;

import java.util.List;

public record ConversationDetailResponse(
    ConversationResponse conversation,
    List<ConversationParticipantResponse> participants
) {}
