package com.giant.employee.dto.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class EmployeeProfileResponseDto (
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Schema(type = "string", example = "1")
    val employeeId: Long,
    val employeeCode: String,
    val employeeName: String,
    val positionName: String,
    val employeeRoleName: String,
    val departmentName: String,
    val teamName: String,
    val phoneNumber: String,
    val email: String,
    val createdEmployeeName: String,
    val updatedEmployeeName: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val leftAt: LocalDateTime
)