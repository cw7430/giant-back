package com.giant.auth.dto

data class SignInDto (
    val accountId: Long,
    val passwordHash: String,
    val accountRoleId: Long,
    val accountRoleName: String,
    val employeeRoleName: String,
    val departmentName: String,
    val teamName: String
)