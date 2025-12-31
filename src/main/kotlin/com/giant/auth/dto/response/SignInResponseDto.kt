package com.giant.auth.dto.response

data class SignInResponseDto(
    val accessToken: String,
    val expiresAt: Long,
    val accountRole: String,
    val employeeRole: String
)
