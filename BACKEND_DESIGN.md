# Chat Application - Backend Design Document

## Project Goal

Build a production-ready chat application using:

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- PostgreSQL
- Liquibase
- WebSocket
- Angular (Frontend)

The application should initially focus only on chat functionality and be designed so future features can be added without redesigning the database.

---

# Version 1 Features

## Authentication

- Register
- Login
- Logout
- JWT Authentication
- Refresh Token

---

## User

- Search user by username
- Send Friend Request
- Accept Friend Request
- Reject Friend Request
- Contact List
- Online Status
- Last Seen

---

## One-to-One Chat

- Create private conversation
- Send messages
- Receive messages
- View message history
- Typing Indicator
- Single Tick
- Double Tick
- Blue Tick
- Edit Message
- Delete Message

---

## Group Chat

- Create Group
- Add Members
- Remove Members
- Leave Group
- Send Messages
- Group Message History

---

## Future Features (Not in Version 1)

- File Sharing
- Image Sharing
- Voice Messages
- Video Calling
- Message Reactions
- Notifications
- Pinned Messages
- Archived Chats
- User Blocking

---

# Entity List

- BaseEntity
- User
- FriendRequest
- Contact
- Conversation
- ConversationParticipant
- Message
- MessageReceipt
- RefreshToken

---

# BaseEntity

Common fields inherited by all entities.

Fields

- id
- createdAt
- updatedAt

Purpose

Avoid duplicate fields across entities.

---

# User

Fields

- id
- username
- password
- displayName
- profilePicture
- enabled
- online
- lastSeen
- createdAt
- updatedAt

Relationships

- One User -> Many Friend Requests (Sender)
- One User -> Many Friend Requests (Receiver)
- One User -> Many Contacts (Owner)
- One User -> Many Contacts (Contact)
- One User -> Many ConversationParticipants
- One User -> Many Messages
- One User -> Many RefreshTokens
- One User -> Many Conversations (Created By)

Notes

- Username must be unique.
- Password will be stored using BCrypt.
- Online status will be updated when the user connects/disconnects.
- Last Seen will be updated when the user goes offline.

---

# FriendRequest

Purpose

Store pending friend requests before users become contacts.

Fields

- id
- sender
- receiver
- status
- createdAt

Relationships

ManyToOne -> User (Sender)

ManyToOne -> User (Receiver)

Status Enum

- PENDING
- ACCEPTED
- REJECTED

Constraints

Unique(sender, receiver)

---

# Contact

Purpose

Stores the contact list.

Fields

- id
- owner
- contact
- createdAt

Relationships

ManyToOne -> User (Owner)

ManyToOne -> User (Contact)

Example

If Bishnu and Rahul become friends

Create two records

Owner = Bishnu

Contact = Rahul

Owner = Rahul

Contact = Bishnu

Constraints

Unique(owner, contact)

Future Expansion

- nickname
- blocked
- muted
- favourite

---

# Conversation

Purpose

Represents both

- Private Chat
- Group Chat

Fields

- id
- type
- name
- createdBy
- createdAt
- updatedAt

Relationships

ManyToOne -> User (Created By)

OneToMany -> ConversationParticipant

OneToMany -> Message

ConversationType Enum

- PRIVATE
- GROUP

Notes

Private conversations have

name = null

Group conversations have a group name.

---

# ConversationParticipant

Purpose

Stores users belonging to conversations.

Fields

- id
- conversation
- user
- role
- joinedAt

Relationships

ManyToOne -> Conversation

ManyToOne -> User

Role Enum

- ADMIN
- MEMBER

Future

OWNER

Constraint

Unique(conversation, user)

---

# Message

Purpose

Stores chat messages.

Fields

- id
- conversation
- sender
- content
- edited
- deleted
- createdAt
- updatedAt

Relationships

ManyToOne -> Conversation

ManyToOne -> User

OneToMany -> MessageReceipt

Notes

There is NO receiver field.

The receiver(s) are determined using ConversationParticipant.

---

# MessageReceipt

Purpose

Tracks message delivery and read status for every recipient.

Fields

- id
- message
- recipient
- status
- deliveredAt
- readAt

Relationships

ManyToOne -> Message

ManyToOne -> User (Recipient)

MessageStatus Enum

- SENT
- DELIVERED
- READ

WhatsApp Tick Mapping

SENT

✔

DELIVERED

✔✔

READ

✔✔ Blue

Why MessageReceipt?

For group chats every recipient can have a different status.

Example

Message

Hello

Rahul -> READ

Aman -> DELIVERED

Rohit -> SENT

This design supports both private chats and groups.

Constraint

Unique(message, recipient)

---

# RefreshToken

Purpose

Store refresh tokens for JWT authentication.

Fields

- id
- user
- token
- expiryDate
- revoked

Relationships

ManyToOne -> User

Purpose

- Refresh Access Token
- Logout
- Revoke Sessions

---

# Relationship Diagram

                        User
                         │
      ┌──────────────────┼──────────────────┐
      │                  │                  │
      │                  │                  │
FriendRequest       Contact         RefreshToken
(sender/receiver) (owner/contact)
│
│
└──────────────────────┐
│
ConversationParticipant
│
│
Conversation
│
│
Message
│
│
MessageReceipt

---

# Cascade Strategy

User

No Cascade REMOVE

Reason

Deleting a user must never automatically delete chats or conversations.

Conversation

Cascade

Conversation

↓

Participants

Conversation

↓

Messages

Deleting a conversation deletes its participants and messages.

Message

Cascade

Message

↓

Receipts

Deleting a message deletes its receipts.

---

# Fetch Strategy

Use FetchType.LAZY for almost every relationship.

Examples

User -> Messages

Conversation -> Participants

Conversation -> Messages

Reason

Avoid loading unnecessary object graphs and improve performance.

---

# Unique Constraints

User

username

Contact

(owner, contact)

FriendRequest

(sender, receiver)

ConversationParticipant

(conversation, user)

MessageReceipt

(message, recipient)

---

# Database Indexes

Create indexes on

username

conversation_id

sender_id

recipient_id

owner_id

contact_id

Reason

Improve search speed and message retrieval performance.

---

# Private Conversation Rule

There must only be one PRIVATE conversation between two users.

When User A opens chat with User B

Search for an existing PRIVATE conversation.

If found

Reuse it.

If not found

Create one.

Never create duplicate private conversations.

---

# Online Status

Stored in User

Fields

online

lastSeen

Future

Redis may replace this for scalability.

---

# Typing Indicator

Do NOT store in database.

Handled completely through WebSocket.

---

# Future Scalability

The database design is intentionally prepared for future features such as

- File Sharing
- Voice Notes
- Video Calls
- Message Reactions
- Notifications
- User Blocking
- Favourite Contacts
- Muted Chats
- Archived Chats
- Group Ownership
- Redis Integration
- Kafka Integration

without requiring major database redesign.