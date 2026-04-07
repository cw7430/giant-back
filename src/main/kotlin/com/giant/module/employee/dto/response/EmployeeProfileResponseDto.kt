package com.giant.module.employee.dto.response

import com.giant.module.employee.entity.EmployeeProfileView
import com.giant.module.employee.entity.type.EmployeeRole
import io.swagger.v3.oas.annotations.media.Schema
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.time.Instant

data class EmployeeProfileResponseDto(
    @get:Schema(description = "일련번호", example = "1", type = "string")
    @get:JsonSerialize(using = ToStringSerializer::class)
    val employeeId: Long,

    @get:Schema(description = "사번", example = "EMP001")
    val employeeCode: String,

    @get:Schema(description = "직급 권한", example = "EMPLOYEE")
    val employeeRole: EmployeeRole,

    @get:Schema(description = "사원 이름", example = "이사장")
    val employeeName: String,

    @get:Schema(description = "직급 코드", example = "PSN10")
    val positionCode: String,

    @get:Schema(description = "직급 이름", example = "대표")
    val positionName: String,

    @get:Schema(description = "부서 코드", example = "DPT100")
    val departmentCode: String,

    @get:Schema(description = "부서 이름", example = "경영지원부")
    val departmentName: String,

    @get:Schema(description = "팀 코드", example = "TM100")
    val teamCode: String,

    @get:Schema(description = "부서 이름", example = "경영팀")
    val teamName: String,

    @get:Schema(description = "휴대전화 번호", example = "010-1234-5678")
    val phoneNumber: String,

    @get:Schema(description = "이메일", example = "example@example.com")
    val email: String,

    @get:Schema(description = "작성자 일련번호", example = "1", type = "string")
    @get:JsonSerialize(using = ToStringSerializer::class)
    val createdBy: Long?,

    @get:Schema(description = "작성자 이름", example = "이사장")
    val createdEmployeeName: String?,

    @get:Schema(description = "수정자 일련번호", example = "1", type = "string")
    @get:JsonSerialize(using = ToStringSerializer::class)
    val updatedBy: Long?,

    @get:Schema(description = "수정자 이름", example = "이사장")
    val updatedEmployeeName: String?,

    @get:Schema(description = "입사일", example = "2006-03-02T12:30:44.461Z")
    val createdAt: Instant,

    @get:Schema(description = "수정일", example = "2006-03-02T12:30:44.461Z")
    val updatedAt: Instant,

    @get:Schema(description = "퇴사일", example = "null")
    val leftAt: Instant?
) {
    companion object {
        fun from(entity: EmployeeProfileView) = EmployeeProfileResponseDto(
            entity.employeeId,
            entity.employeeCode,
            entity.employeeRole,
            entity.employeeName,
            entity.positionCode,
            entity.positionName,
            entity.departmentCode,
            entity.departmentName,
            entity.teamCode,
            entity.teamName,
            entity.phoneNumber,
            entity.email,
            entity.createdBy,
            entity.createdEmployeeName,
            entity.updatedBy,
            entity.updatedEmployeeName,
            entity.createdAt,
            entity.updatedAt,
            entity.leftAt
        )
    }
}
