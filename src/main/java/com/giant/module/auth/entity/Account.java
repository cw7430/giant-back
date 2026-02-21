package com.giant.module.auth.entity;

import com.giant.module.auth.entity.type.AuthRole;
import com.giant.module.employee.entity.EmployeeProfile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Table(
        name = "account",
        schema = "auth"
)
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

    @Column(
            name = "auth_role",
            nullable = false,
            columnDefinition = "auth.auth_role DEFAULT 'USER'::auth.auth_role"
    )
    @Enumerated(EnumType.STRING)
    private AuthRole authRole = AuthRole.USER;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP(6) WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')"
    )
    private Instant createdAt;

    @Column(
            name = "updated_at",
            nullable = false,
            columnDefinition = "TIMESTAMP(6) WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')"
    )
    private Instant updatedAt;

    @Column(
            name = "deleted_at",
            columnDefinition = "TIMESTAMP(6) WITH TIME ZONE"
    )
    private Instant deletedAt;

    @OneToOne(mappedBy = "account")
    private EmployeeProfile employeeProfile;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<RefreshToken> refreshTokens;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}

/*
constraint ck_user_deleted_state
        check (((auth_role <> 'LEFT'::auth.auth_role) AND (deleted_at IS NULL)) OR
               ((auth_role = 'LEFT'::auth.auth_role) AND (deleted_at IS NOT NULL)))
*/
/*
create unique index uq_user_name
    on auth.account (user_name)
    where (auth_role <> 'LEFT'::auth.auth_role);
*/
/*
create index ix_active_user_created_at
    on auth.account (created_at desc)
    where (auth_role <> 'LEFT'::auth.auth_role);
*/
/*
create index ix_delete_user_deleted_at
    on auth.account (deleted_at desc)
    where (auth_role = 'LEFT'::auth.auth_role);
*/

