package com.bishnu.chatapplication.dto;

public record CreateMessageRequest(String content, Long replyToMessageId) {}
