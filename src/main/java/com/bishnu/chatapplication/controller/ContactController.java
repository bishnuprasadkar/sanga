package com.bishnu.chatapplication.controller;

import com.bishnu.chatapplication.dto.ContactResponse;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @GetMapping
    public ResponseEntity<List<ContactResponse>> getContacts(Principal principal) {
        // TODO: delegate to ContactService.findAll(principal.getName())
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{contactUserId}")
    public ResponseEntity<Void> removeContact(@PathVariable Long contactUserId, Principal principal) {
        // TODO: delegate to ContactService.remove(contactUserId, principal.getName())
        return ResponseEntity.noContent().build();
    }
}
