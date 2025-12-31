package com.giant.auth.repository

import com.giant.auth.entity.Account
import com.giant.auth.repository.custom.AccountRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository:
    JpaRepository<Account, Long>,
    AccountRepositoryCustom {
}