package com.giant.module.auth.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "refresh_token",
    schema = "auth",
    indexes = [
        Index(name = "ix_token_account", columnList = "account_id"),
        Index(name = "ix_token_expires_at", columnList = "expires_at"),
    ]
)
class RefreshToken(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = ForeignKey(name = "fk_token_account"))
    var account: Account,

    @Column(name = "token", nullable = false, unique = true)
    var token: String,

    @Column(name = "expires_at", nullable = false)
    var expiresAt: Instant
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var refreshTokenId: Long? = null
        protected set

    companion object {
        fun create(account: Account, token: String, expiresAt: Instant): RefreshToken {
            return RefreshToken(
                account = account,
                token = token,
                expiresAt = expiresAt
            )
        }
    }
}