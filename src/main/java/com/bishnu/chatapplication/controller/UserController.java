package com.bishnu.chatapplication.controller;

import com.bishnu.chatapplication.dto.CurrentUserResponse;
import com.bishnu.chatapplication.dto.UpdateProfileRequest;
import com.bishnu.chatapplication.dto.UpdatePresenceRequest;
import com.bishnu.chatapplication.dto.UserSummaryResponse;
import java.security.Principal;
import java.util.List;

import com.bishnu.chatapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.getCurrentUser(principal.getName()));
    }

    @PatchMapping("/me")
    public ResponseEntity<CurrentUserResponse> updateCurrentUser(
        Principal principal,
        @RequestBody UpdateProfileRequest request
    ) {
        return ResponseEntity.ok(userService.updateProfile(principal.getName(), request));
    }

    @PatchMapping("/me/presence")
    public ResponseEntity<Void> updatePresence(
        @RequestBody UpdatePresenceRequest request,
        Principal principal
    ) {
        // TODO: delegate to PresenceService.update(principal.getName(), request)
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSummaryResponse>> searchUsers(
        @RequestParam String query,
        Principal principal
    ) {
        return ResponseEntity.ok(userService.searchUsers(query, principal.getName()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserSummaryResponse> getUserById(@PathVariable Long userId, Principal principal) {
        return ResponseEntity.ok(userService.getUserById(userId, principal.getName()));
    }
}
