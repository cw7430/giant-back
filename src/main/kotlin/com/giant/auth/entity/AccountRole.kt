package com.giant.auth.entity

import jakarta.persistence.*

@Entity
@Table(name = "account_role", schema = "auth")
data class AccountRole(
    @Id
    @Column(name = "id")
    val accountRoleId: Long,

    @Column(name = "role_name", nullable = false, unique = true, length = 25)
    val accountRoleName: String,

    @OneToMany(mappedBy = "accountRole", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val accounts: MutableList<Account> = mutableListOf()
)