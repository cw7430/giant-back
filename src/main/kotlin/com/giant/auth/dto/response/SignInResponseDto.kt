package com.giant.auth.dto.response

data class SignInResponseDto(
    val accessToken: String,
    val expiresIn: Long,
    val accountRole: String,
    val employeeRole: String
)
