package com.bishnu.chatapplication.entity;

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
    name = "contacts",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_contact_owner_contact",
            columnNames = {"owner_id", "contact_id"}
        )
    },
    indexes = {
        @Index(name = "idx_contact_owner", columnList = "owner_id"),
        @Index(name = "idx_contact_contact", columnList = "contact_id")
    }
)
public class Contact extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_id", nullable = false)
    private User contact;

}
