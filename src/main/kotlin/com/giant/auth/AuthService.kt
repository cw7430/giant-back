package com.giant.auth

import com.giant.auth.repository.AccountRepository
import com.giant.auth.repository.AccountRoleRepository
import org.springframework.stereotype.Service

@Service
class AuthService (
    val accountRepository: AccountRepository,
    val accountRoleRepository: AccountRoleRepository
) {
}