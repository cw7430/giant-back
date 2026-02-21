package com.giant.module.auth.repository

import com.giant.module.auth.entity.Account
import com.giant.module.auth.repository.custom.AccountRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long>, AccountRepositoryCustom {
    fun existsByUserName(userName: String): Boolean
}