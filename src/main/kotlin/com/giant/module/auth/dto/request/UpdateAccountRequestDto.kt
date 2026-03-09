package com.giant.module.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

@Schema(name = "UpdateAccountRequest")
data class UpdateAccountRequestDto(
    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    @get:Schema(description = "비빌번호", example = "examplepw1234")
    val password: String,

    @field:Pattern(
        regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$",
        message = "전화번호 형식이 올바르지 않습니다."
    )
    @get:Schema(description = "휴대전화 번호", example = "010-1234-5678")
    val phoneNumber: String?,

    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    @get:Schema(description = "이메일", example = "example@example.com")
    val email: String?
)