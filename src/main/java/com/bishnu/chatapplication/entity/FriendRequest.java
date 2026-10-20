package com.bishnu.chatapplication.entity;

import com.bishnu.chatapplication.enums.FriendRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(
    name = "friend_requests",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_friend_request_sender_receiver",
            columnNames = {"sender_id", "receiver_id"}
        )
    },
    indexes = {
        @Index(name = "idx_friend_request_sender", columnList = "sender_id"),
        @Index(name = "idx_friend_request_receiver", columnList = "receiver_id")
    }
)
public class FriendRequest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @lombok.Builder.Default
    private FriendRequestStatus status = FriendRequestStatus.PENDING;

}
