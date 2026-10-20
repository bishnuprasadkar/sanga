package com.bishnu.chatapplication.entity;

import com.bishnu.chatapplication.enums.ConversationType;
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
    name = "conversations",
    indexes = {
        @Index(name = "idx_conversation_type", columnList = "type"),
        @Index(name = "idx_conversation_created_by", columnList = "created_by")
    }
)
public class Conversation extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ConversationType type;

    @Column(length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(
        mappedBy = "conversation",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @lombok.Builder.Default
    private List<ConversationParticipant> participants = new ArrayList<>();

    @OneToMany(
        mappedBy = "conversation",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @lombok.Builder.Default
    private List<Message> messages = new ArrayList<>();
}
