package com.giant.module.employee.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer

data class TeamResponseDto(
    @get:Schema(description = "일련번호", example = "1", type = "string")
    @get:JsonSerialize(using = ToStringSerializer::class)
    val teamId: Long,

    @get:Schema(description = "팀 코드", example = "TM100")
    val teamCode: String,

    @get:Schema(description = "부서 이름", example = "경영팀")
    val teamName: String,
)