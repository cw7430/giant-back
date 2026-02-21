package com.giant.module.auth.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "refresh_token",
    schema = "auth",
    indexes = [Index(name = "ix_token_account", columnList = "account_id")]
)
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val refreshTokenId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = ForeignKey(name = "fk_token_account"))
    var account: Account? = null,

    @Column(name = "token", nullable = false, unique = true)
    var token: String? = null,

    @Column(name = "expires_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP(6) WITH TIME ZONE")
    var expiresAt: Instant? = null
) {
    companion object {
        fun create(account: Account, token: String, expiresAt: Instant): RefreshToken {
            return RefreshToken(
                account = account,
                token = token,
                expiresAt = expiresAt
            )
        }
    }

    fun update(account: Account, token: String, expiresAt: Instant): RefreshToken {
        this.account = account
        this.token = token
        this.expiresAt = expiresAt
        return this
    }
}