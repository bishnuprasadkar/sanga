package com.bishnu.chatapplication.controller;

import com.bishnu.chatapplication.dto.CreateMessageRequest;
import com.bishnu.chatapplication.dto.MessageResponse;
import com.bishnu.chatapplication.dto.UpdateMessageRequest;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversations/{conversationId}/messages")
public class MessageController {

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getMessages(
        @PathVariable Long conversationId,
        @RequestParam(required = false) Long beforeMessageId,
        @RequestParam(defaultValue = "50") int limit,
        Principal principal
    ) {
        // TODO: delegate to MessageService.findHistory(conversationId, beforeMessageId, limit, principal.getName())
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(
        @PathVariable Long conversationId,
        @RequestBody CreateMessageRequest request,
        Principal principal
    ) {
        // TODO: delegate to MessageService.send(conversationId, request, principal.getName())
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<MessageResponse> editMessage(
        @PathVariable Long conversationId,
        @PathVariable Long messageId,
        @RequestBody UpdateMessageRequest request,
        Principal principal
    ) {
        // TODO: delegate to MessageService.edit(conversationId, messageId, request, principal.getName())
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(
        @PathVariable Long conversationId,
        @PathVariable Long messageId,
        Principal principal
    ) {
        // TODO: delegate to MessageService.delete(conversationId, messageId, principal.getName())
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{messageId}/receipts/delivered")
    public ResponseEntity<Void> markDelivered(
        @PathVariable Long conversationId,
        @PathVariable Long messageId,
        Principal principal
    ) {
        // TODO: delegate to MessageReceiptService.markDelivered(conversationId, messageId, principal.getName())
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{messageId}/receipts/read")
    public ResponseEntity<Void> markRead(
        @PathVariable Long conversationId,
        @PathVariable Long messageId,
        Principal principal
    ) {
        // TODO: delegate to MessageReceiptService.markRead(conversationId, messageId, principal.getName())
        return ResponseEntity.noContent().build();
    }
}
