package com.bishnu.chatapplication.controller;

import com.bishnu.chatapplication.dto.AddConversationMembersRequest;
import com.bishnu.chatapplication.dto.ConversationDetailResponse;
import com.bishnu.chatapplication.dto.ConversationResponse;
import com.bishnu.chatapplication.dto.CreateGroupConversationRequest;
import com.bishnu.chatapplication.dto.CreatePrivateConversationRequest;
import com.bishnu.chatapplication.dto.UpdateGroupConversationRequest;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @PostMapping("/private")
    public ResponseEntity<ConversationDetailResponse> createOrGetPrivateConversation(
        @RequestBody CreatePrivateConversationRequest request,
        Principal principal
    ) {
        // TODO: delegate to ConversationService.createOrGetPrivate(request, principal.getName())
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/groups")
    public ResponseEntity<ConversationDetailResponse> createGroup(
        @RequestBody CreateGroupConversationRequest request,
        Principal principal
    ) {
        // TODO: delegate to ConversationService.createGroup(request, principal.getName())
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ConversationResponse>> getConversations(Principal principal) {
        // TODO: delegate to ConversationService.findAllForUser(principal.getName())
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationDetailResponse> getConversation(
        @PathVariable Long conversationId,
        Principal principal
    ) {
        // TODO: delegate to ConversationService.getById(conversationId, principal.getName())
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{conversationId}")
    public ResponseEntity<ConversationDetailResponse> updateGroup(
        @PathVariable Long conversationId,
        @RequestBody UpdateGroupConversationRequest request,
        Principal principal
    ) {
        // TODO: delegate to ConversationService.updateGroup(conversationId, request, principal.getName())
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{conversationId}/members")
    public ResponseEntity<ConversationDetailResponse> addMembers(
        @PathVariable Long conversationId,
        @RequestBody AddConversationMembersRequest request,
        Principal principal
    ) {
        // TODO: delegate to ConversationService.addMembers(conversationId, request, principal.getName())
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{conversationId}/members/{userId}")
    public ResponseEntity<Void> removeMember(
        @PathVariable Long conversationId,
        @PathVariable Long userId,
        Principal principal
    ) {
        // TODO: delegate to ConversationService.removeMember(conversationId, userId, principal.getName())
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{conversationId}/leave")
    public ResponseEntity<Void> leaveConversation(@PathVariable Long conversationId, Principal principal) {
        // TODO: delegate to ConversationService.leave(conversationId, principal.getName())
        return ResponseEntity.noContent().build();
    }
}
