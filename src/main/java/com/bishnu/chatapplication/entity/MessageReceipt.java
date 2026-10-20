package com.bishnu.chatapplication.entity;

import com.bishnu.chatapplication.enums.MessageStatus;
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
    name = "message_receipts",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_message_recipient",
            columnNames = {"message_id", "recipient_id"}
        )
    },
    indexes = {
        @Index(name = "idx_receipt_message", columnList = "message_id"),
        @Index(name = "idx_receipt_recipient", columnList = "recipient_id"),
        @Index(name = "idx_receipt_status", columnList = "status")
    }
)
public class MessageReceipt extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @lombok.Builder.Default
    private MessageStatus status = MessageStatus.SENT;

    @Column(name = "delivered_at")
    private Instant deliveredAt;

    @Column(name = "read_at")
    private Instant readAt;
}
