package com.giant.common.config.security.type

data class TokenResponseClaim(
    val token: String,
    val expiresAtMs: Long
)
