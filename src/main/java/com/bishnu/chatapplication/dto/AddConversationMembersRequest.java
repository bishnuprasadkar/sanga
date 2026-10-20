package com.bishnu.chatapplication.dto;

import java.util.List;

public record AddConversationMembersRequest(List<Long> userIds) {}
