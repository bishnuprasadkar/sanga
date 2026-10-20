package com.bishnu.chatapplication.controller;

import com.bishnu.chatapplication.dto.CreateFriendRequest;
import com.bishnu.chatapplication.dto.FriendRequestResponse;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {

    @PostMapping
    public ResponseEntity<FriendRequestResponse> sendFriendRequest(
        @RequestBody CreateFriendRequest request,
        Principal principal
    ) {
        // TODO: delegate to FriendRequestService.send(request, principal.getName())
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<FriendRequestResponse>> getFriendRequests(
        @RequestParam(defaultValue = "received") String direction,
        Principal principal
    ) {
        // TODO: delegate to FriendRequestService.findAll(direction, principal.getName())
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{requestId}/accept")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Long requestId, Principal principal) {
        // TODO: delegate to FriendRequestService.accept(requestId, principal.getName())
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{requestId}/reject")
    public ResponseEntity<Void> rejectFriendRequest(@PathVariable Long requestId, Principal principal) {
        // TODO: delegate to FriendRequestService.reject(requestId, principal.getName())
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> cancelFriendRequest(@PathVariable Long requestId, Principal principal) {
        // TODO: delegate to FriendRequestService.cancel(requestId, principal.getName())
        return ResponseEntity.noContent().build();
    }
}
