# Chat Application - Context Capsule

## Project Overview

This is a production-style backend chat application built for learning, portfolio, and interview preparation.

The project prioritizes clean architecture, scalability, and production-ready design over rapid feature implementation.

Frontend will be Angular.

Backend will be Spring Boot.

---

# Technology Stack

- Java 21
- Spring Boot 3.5.x
- Spring Security
- JWT Authentication
- PostgreSQL
- Liquibase
- WebSocket
- Maven
- Lombok
- JPA / Hibernate

Future

- Redis
- Kafka
- Docker

---

# Authentication

Do NOT use Keycloak.

Authentication must be implemented manually using:

- Spring Security
- JWT Access Token
- Refresh Token

Passwords must be encrypted using BCrypt.

---

# Project Goal

Version 1 only focuses on chat.

Features

- Register
- Login
- JWT Authentication
- Refresh Token
- Logout

User Features

- Search users by username
- Send Friend Request
- Accept Friend Request
- Reject Friend Request
- Contact List

Chat

- One-to-One Chat
- Group Chat
- Message History
- Reply to Message
- Edit Message
- Delete Message

Real Time

- Online / Offline
- Last Seen
- Typing Indicator
- Single Tick
- Double Tick
- Blue Tick

Not included in Version 1

- File Sharing
- Voice Messages
- Calls
- Notifications
- Message Reactions

---

# Time Handling

All timestamps MUST use UTC.

Java

Use

Instant

Never use

LocalDateTime

Database

TIMESTAMPTZ

Frontend converts UTC to the user's local timezone.

---

# ORM Rules

Use

FetchType.LAZY

for almost every relationship.

Avoid EAGER loading.

Only use CascadeType.ALL where ownership actually exists.

Do not use Cascade REMOVE from User to child entities.

---

# Entity List

BaseEntity

User

FriendRequest

Contact

Conversation

ConversationParticipant

Message

MessageReceipt

RefreshToken

---

# BaseEntity

Contains

- id
- createdAt
- updatedAt

Uses

@MappedSuperclass

Not an entity.

No Liquibase migration.

---

# User

Fields

- username
- password
- displayName
- profilePicture
- active
- onlineStatus
- lastSeen

Relationships

FriendRequest

Contact

ConversationParticipant

Conversation

Message

MessageReceipt

RefreshToken

---

# FriendRequest

Fields

sender

receiver

status

Status

PENDING

ACCEPTED

REJECTED

---

# Contact

Stores accepted friends.

One accepted request creates

Owner -> Contact

Contact -> Owner

---

# Conversation

Represents both

PRIVATE

GROUP

Group conversations have a name.

Private conversations reuse the same conversation between two users.

Never create duplicate private conversations.

---

# ConversationParticipant

Stores users belonging to conversations.

Role

ADMIN

MEMBER

---

# Message

Stores

Conversation

Sender

Content

Edited

Deleted

ReplyToMessage

No receiver field.

Receivers are determined using ConversationParticipant.

---

# MessageReceipt

Tracks message state for every recipient.

Status

SENT

DELIVERED

READ

Never store status inside Message.

Group chats require per-user status.

---

# RefreshToken

Stores

User

Token

Expiry

Revoked

---

# Database Design Rules

Never rely on Hibernate DDL generation.

Always create database changes using Liquibase.

Liquibase is the single source of truth.

Migration order

001_create_users_table

002_create_friend_requests_table

003_create_contacts_table

004_create_conversations_table

005_create_conversation_participants_table

006_create_messages_table

007_create_message_receipts_table

008_create_refresh_tokens_table

---

# Foreign Key Rules

User relationships

RESTRICT

Conversation -> Participants

CASCADE

Conversation -> Messages

CASCADE

Message -> MessageReceipt

CASCADE

RefreshToken -> User

CASCADE

---

# Coding Standards

Package names must be lowercase.

Use constructor injection.

Never use field injection.

Return DTOs.

Never expose entities directly through controllers.

Keep business logic inside Services.

Controllers should remain thin.

Repositories should only contain persistence logic.

Do not duplicate code.

Prefer readability over clever code.

---

# API Design

RESTful APIs.

Use consistent response DTOs.

Validate all request DTOs.

Never return stack traces to clients.

Use global exception handling.

---

# WebSocket

Use WebSocket only for

- Chat messages
- Typing indicator
- Online/Offline
- Read receipts
- Delivered receipts

Typing indicator is NOT stored in the database.

---

# Future Features

Design should allow adding later without redesigning entities.

Future

- File Sharing
- Voice Messages
- Video Calls
- Notifications
- Reactions
- Redis
- Kafka

---

# Important Instruction

When suggesting code:

- Respect the existing architecture.
- Do not replace Liquibase with Hibernate DDL.
- Do not replace Instant with LocalDateTime.
- Do not introduce unnecessary libraries.
- Prefer scalable, production-ready implementations.
- If a proposed change affects the database schema, explain why before modifying the design.