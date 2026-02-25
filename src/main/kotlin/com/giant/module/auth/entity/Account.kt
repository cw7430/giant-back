package com.giant.module.auth.entity

import com.giant.module.auth.entity.type.AuthRole
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType
import java.time.Instant

@Entity
@Table(name = "account", schema = "auth")
@DynamicUpdate
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
    @JdbcType(PostgreSQLEnumJdbcType::class)
    @Enumerated(EnumType.STRING)
    var authRole: AuthRole = AuthRole.USER,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val accountId: Long? = null

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    lateinit var createdAt: Instant

    @Column(
        name = "updated_at",
        nullable = false
    )
    lateinit var updatedAt: Instant

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

    fun updatePassword(passwordHash: String): Account {
        this.passwordHash = passwordHash
        return this
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
