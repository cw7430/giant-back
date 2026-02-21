package com.giant.module.auth.dto.response

import com.giant.module.auth.entity.type.AuthRole
import com.giant.module.employee.entity.type.EmployeeRole

data class SignInResponseDto(
    val accessToken: String,
    val accessTokenExpiresAtMs: Long,
    val refreshToken: String,
    val refreshTokenExpiresAtMs: Long,
    val isAuto: Boolean,
    val employeeCode: String,
    val employeeName: String,
    val authRole: AuthRole,
    val employeeRole: EmployeeRole,
    val department: String,
    val team: String,
    val position: String
) 