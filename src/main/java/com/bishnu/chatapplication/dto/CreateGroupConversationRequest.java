package com.bishnu.chatapplication.dto;

import java.util.List;

public record CreateGroupConversationRequest(String name, List<Long> memberUserIds) {}
