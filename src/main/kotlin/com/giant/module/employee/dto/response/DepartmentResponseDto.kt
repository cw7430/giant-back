package com.giant.module.employee.dto.response

import com.giant.module.employee.entity.Department
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer

data class DepartmentResponseDto(
    @get:Schema(description = "일련번호", example = "1", type = "string")
    @get:JsonSerialize(using = ToStringSerializer::class)
    val departmentId: Long,

    @get:Schema(description = "부서 코드", example = "DPT100")
    val departmentCode: String,

    @get:Schema(description = "부서 이름", example = "경영지원부")
    val departmentName: String,

    @get:ArraySchema(schema = Schema(implementation = TeamResponseDto::class))
    val teams: List<TeamResponseDto>? = null
) {
    companion object {
        fun from(entity: Department) = DepartmentResponseDto(
            entity.departmentId!!,
            entity.departmentCode,
            entity.departmentName,
            entity.teams.map { TeamResponseDto.from(it) }
        )
    }
}