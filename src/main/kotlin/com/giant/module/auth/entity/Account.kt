package com.giant.module.auth.entity

import com.giant.module.auth.entity.type.AuthRole
import com.giant.module.employee.entity.EmployeeProfile
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "account", schema = "auth")
class Account(
    @Column(name = "user_name", nullable = false, length = 25)
    var userName: String,

    @Column(name = "password_hash", nullable = false)
    var passwordHash: String,

    @Column(name = "phone_number", nullable = false, length = 15)
    var phoneNumber: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "auth_role", nullable = false)
    @Enumerated(EnumType.STRING)
    var authRole: AuthRole = AuthRole.USER,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var accountId: Long? = null
        protected set

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    var createdAt: Instant = Instant.now()

    @Column(
        name = "updated_at",
        nullable = false
    )
    var updatedAt: Instant = Instant.now()

    @OneToOne(mappedBy = "account", cascade = [CascadeType.ALL])
    var employeeProfile: EmployeeProfile? = null
        protected set

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    private val _refreshTokens: MutableList<RefreshToken> = mutableListOf()

    val refreshTokens: List<RefreshToken>
        get() = _refreshTokens.toList()

    @PrePersist
    fun onCreate() {
        val now = Instant.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = Instant.now()
    }

    fun withdraw() {
        this.authRole = AuthRole.LEFT
        this.deletedAt = Instant.now()
    }

    companion object {
        fun create(
            userName: String,
            passwordHash: String,
            phoneNumber: String,
            email: String
        ): Account {
            return Account(userName, passwordHash, phoneNumber, email)
        }
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

