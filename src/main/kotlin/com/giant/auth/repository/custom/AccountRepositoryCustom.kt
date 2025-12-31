package com.giant.auth.repository.custom

import com.giant.auth.dto.SignInDto

interface AccountRepositoryCustom {
    fun findSignInInfoByUserName(userName: String): SignInDto?
}