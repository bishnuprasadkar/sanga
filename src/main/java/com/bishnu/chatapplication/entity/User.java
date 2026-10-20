package com.bishnu.chatapplication.entity;

import com.bishnu.chatapplication.enums.OnlineStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(name = "profile_picture", length = 500)
    private String profilePicture;

    @Column(nullable = false)
    @lombok.Builder.Default
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @lombok.Builder.Default
    private OnlineStatus onlineStatus = OnlineStatus.OFFLINE;

    @Column(name = "last_seen")
    private Instant lastSeen;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @lombok.Builder.Default
    private List<FriendRequest> sentFriendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @lombok.Builder.Default
    private List<FriendRequest> receivedFriendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @lombok.Builder.Default
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY)
    @lombok.Builder.Default
    private List<Contact> addedByUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @lombok.Builder.Default
    private List<ConversationParticipant> conversationParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @lombok.Builder.Default
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    @lombok.Builder.Default
    private List<MessageReceipt> messageReceipts = new ArrayList<>();

    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @lombok.Builder.Default
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    @lombok.Builder.Default
    private List<Conversation> createdConversations = new ArrayList<>();

}
