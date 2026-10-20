package com.bishnu.chatapplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(
    name = "messages",
    indexes = {
        @Index(name = "idx_message_conversation", columnList = "conversation_id"),
        @Index(name = "idx_message_sender", columnList = "sender_id"),
        @Index(name = "idx_message_reply_to", columnList = "reply_to_message_id")
    }
)
public class Message extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @lombok.Builder.Default
    private Boolean edited = false;

    @Column(nullable = false)
    @lombok.Builder.Default
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to_message_id")
    private Message replyToMessage;

    @OneToMany(
        mappedBy = "replyToMessage",
        fetch = FetchType.LAZY
    )
    @lombok.Builder.Default
    private List<Message> replies = new ArrayList<>();

    @OneToMany(
        mappedBy = "message",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @lombok.Builder.Default
    private List<MessageReceipt> receipts = new ArrayList<>();
}
