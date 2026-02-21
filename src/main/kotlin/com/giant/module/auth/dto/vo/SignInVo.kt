package com.giant.module.auth.dto.vo

import com.giant.module.auth.entity.type.AuthRole
import com.giant.module.employee.entity.type.EmployeeRole

data class SignInVo(
    val accountId: Long,
    val passwordHash: String,
    val authRole: AuthRole,
    val employeeCode: String,
    val employeeName: String,
    val employeeRole: EmployeeRole,
    val departmentName: String,
    val teamName: String,
    val positionName: String
)
