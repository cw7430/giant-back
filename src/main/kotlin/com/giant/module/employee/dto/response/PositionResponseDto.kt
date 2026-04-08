package com.giant.module.employee.dto.response

import com.giant.module.employee.entity.Position
import io.swagger.v3.oas.annotations.media.Schema
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer

data class PositionResponseDto(
    @get:Schema(description = "일련번호", example = "1", type = "string")
    @get:JsonSerialize(using = ToStringSerializer::class)
    val positionId: Long,

    @get:Schema(description = "직급 코드", example = "PSN10")
    val positionCode: String,

    @get:Schema(description = "직급 이름", example = "대표")
    val positionName: String,

    @get:Schema(description = "기본급", example = "1000000", type = "string")
    @get:JsonSerialize(using = ToStringSerializer::class)
    val basicSalary: Long,

    @get:Schema(description = "성과급", example = "100000", type = "string")
    @get:JsonSerialize(using = ToStringSerializer::class)
    val incentiveSalary: Long
) {
    companion object {
        fun from(entity: Position) = PositionResponseDto(
            entity.positionId!!,
            entity.positionCode,
            entity.positionName,
            entity.basicSalary,
            entity.incentiveSalary
        )
    }
}