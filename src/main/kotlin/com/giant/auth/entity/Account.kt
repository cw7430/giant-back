package com.giant.auth.entity

import com.giant.employee.entity.EmployeeProfile
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.time.Clock
import java.time.LocalDateTime

@Entity
@Table(
    name = "account",
    schema = "auth",
    indexes = [
        Index(name="idx_account_role_id", columnList = "account_role_id")
    ]
)
@DynamicUpdate
data class Account(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val accountId: Long = 0,

    @Column(
        name = "user_name",
        nullable = false,
        unique = true,
        length = 25,
        columnDefinition = "nvarchar(25) COLLATE Latin1_General_100_CI_AI"
    )
    val userName: String,

    @Column(
        name = "password_hash",
        nullable = false,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    val passwordHash: String,

    @Column(
        name = "phone_number",
        nullable = false,
        length = 15,
        columnDefinition = "nvarchar(15) COLLATE Latin1_General_100_CI_AS_SC"
    )
    val phoneNumber: String,

    @Column(
        name = "email",
        nullable = false,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    val email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "account_role_id",
        nullable = false,
        columnDefinition = "bigint default 2",
        foreignKey = ForeignKey(name = "fk_account_role")
    )
    val accountRole: AccountRole,

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false,
        columnDefinition = "datetime2(3) default SYSUTCDATETIME()"
    )
    var createdAt: LocalDateTime? = null,

    @Column(
        name = "updated_at",
        nullable = false,
        columnDefinition = "datetime2(3) default SYSUTCDATETIME()"
    )
    var updatedAt: LocalDateTime? = null,

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    val employeeProfile: EmployeeProfile? = null
) {
    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now(Clock.systemUTC())
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now(Clock.systemUTC())
    }
}
