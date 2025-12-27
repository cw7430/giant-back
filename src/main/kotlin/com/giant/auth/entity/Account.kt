package com.giant.auth.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "account", schema = "auth")
data class Account(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val accountId: Long,

    @Column(name = "user_name", nullable = false, unique = true, length = 25)
    val userName: String,

    @Column(name = "password", nullable = false, length = 255)
    val password: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_role_id", nullable = false, foreignKey = ForeignKey(name = "fk_account_role"))
    val role: AccountRole,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant
    )
