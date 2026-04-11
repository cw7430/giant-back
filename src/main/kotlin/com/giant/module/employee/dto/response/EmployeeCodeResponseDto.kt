package com.giant.module.employee.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class EmployeeCodeResponseDto(
    @get:Schema(description = "사번", example = "EMP001")
    val employeeCode: String
)
