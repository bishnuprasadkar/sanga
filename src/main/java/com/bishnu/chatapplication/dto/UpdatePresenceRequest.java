package com.bishnu.chatapplication.dto;

import com.bishnu.chatapplication.enums.OnlineStatus;

public record UpdatePresenceRequest(OnlineStatus onlineStatus) {}
