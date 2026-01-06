package com.giant.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;

@Entity
@Table(name = "account", schema = "auth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long accountId;

    @Column(
            name = "user_name",
            nullable = false,
            unique = true,
            length = 25
    )
    private String userName;

    @Column(
            name = "password_hash",
            nullable = false
    )
    private String passwordHash;

    @Column(
            name = "phone_number",
            nullable = false,
            length = 15
    )
    private String phoneNumber;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "account_role_id",
            nullable = false,
            columnDefinition = "BIGINT DEFAULT 2",
            foreignKey = @ForeignKey(name = "fk_account_role")
    )
    private AccountRole accountRole;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP(3) WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false,
            columnDefinition = "TIMESTAMP(3) WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDateTime.now(Clock.systemUTC());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(Clock.systemUTC());
    }

    public void create(
            String userName,
            String passwordHash,
            String phoneNumber,
            String email,
            AccountRole accountRole
    ) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accountRole = accountRole;
    }

    public void updateAccountInfo(String userName, String phoneNumber, String email) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void updatePassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void updateRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }
}
