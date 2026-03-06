package com.giant.module.employee.dto.response

import com.giant.module.employee.entity.type.EmployeeRole
import java.time.Instant

data class EmployeeProfileResponseDto(
    val employeeId: Long,
    val employeeCode: String,
    val employeeRole: EmployeeRole,
    val employeeName: String,
    val positionCode: String,
    val positionName: String,
    val departmentCode: String,
    val departmentName: String,
    val teamCode: String,
    val teamName: String,
    val phoneNumber: String,
    val email: String,
    val createdBy: Long?,
    val createdEmployeeName: String?,
    val updatedBy: Long?,
    val updatedEmployeeName: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
    val leftAt: Instant?
)
