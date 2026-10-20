package com.bishnu.chatapplication.controller;

import com.bishnu.chatapplication.dto.TypingIndicatorRequest;
import java.security.Principal;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    /**
     * Client destination: /app/conversations/{conversationId}/typing.
     * A future implementation should broadcast the indicator to the other participants.
     */
    @MessageMapping("/conversations/{conversationId}/typing")
    public void publishTypingIndicator(
        @DestinationVariable Long conversationId,
        TypingIndicatorRequest request,
        Principal principal
    ) {
        // TODO: validate membership and publish to /topic/conversations/{conversationId}/typing
    }
}
