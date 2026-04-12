package com.giant.module.employee.dto.request

import com.giant.module.employee.entity.type.EmployeeRole
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern

@Schema(name = "UpdateEmployeeProfile")
data class UpdateEmployeeProfileRequestDto(
    @field:Pattern(
        regexp = "^PSN\\d{2,3}$",
        message = "직급 코드 형식이 올바르지 않습니다."
    )
    @get:Schema(description = "직급 코드", example = "PSN10")
    val positionCode: String?,

    @field:Pattern(
        regexp = "^TM\\d{3}$",
        message = "팀 코드 형식이 올바르지 않습니다."
    )
    @get:Schema(description = "팀 코드", example = "TM100")
    val teamCode: String?,

    @get:Schema(description = "직급 권한", example = "EMPLOYEE")
    val employeeRole: EmployeeRole?
)