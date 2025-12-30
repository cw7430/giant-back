package com.giant.common.config.security.dto

data class ClaimDto (
    val userId: String,
    val accountRole: String,
    val employeeRole: String
)