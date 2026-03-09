package com.giant.module.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "SignOutRequest")
data class SignOutRequestDto(
    @get:Schema(description = "refresh token")
    val refreshToken: String?
)
