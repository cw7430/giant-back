package com.giant.auth.entity

import jakarta.persistence.*

@Entity
@Table(name = "account_role", schema = "auth")
class AccountRole(
    @Id
    @Column(name = "id", nullable = false)
    var accountRoleId: Long = 0,

    @Column(
        name = "role_name",
        nullable = false,
        unique = true,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    var accountRoleName: String = "",

    @OneToMany(mappedBy = "accountRole", fetch = FetchType.LAZY)
    val accounts: MutableList<Account> = mutableListOf()
)