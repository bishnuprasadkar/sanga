package com.bishnu.chatapplication.entity;

import com.bishnu.chatapplication.enums.ConversationRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(
    name = "conversation_participants",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_conversation_user",
            columnNames = {"conversation_id", "user_id"}
        )
    },
    indexes = {
        @Index(name = "idx_participant_conversation", columnList = "conversation_id"),
        @Index(name = "idx_participant_user", columnList = "user_id")
    }
)
public class ConversationParticipant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @lombok.Builder.Default
    private ConversationRole role = ConversationRole.MEMBER;

}
