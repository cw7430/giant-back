package com.giant.auth.repository.custom

import com.giant.auth.dto.SignInDto

interface AccountRepositoryCustom {
    fun findSignInInfoByUserName(userName: String): SignInDto?
    fun findRefreshInfoByAccountId(accountId: Long): SignInDto?
    fun existsByUserName(userName: String): Boolean?
    fun findPasswordHashByAccountId(accountId: Long): String?
    fun updateAccount(accountId: Long, userName: String, phoneNumber: String, email: String): Long
    fun updatePassword(accountId: Long, passwordHash: String): Long
}