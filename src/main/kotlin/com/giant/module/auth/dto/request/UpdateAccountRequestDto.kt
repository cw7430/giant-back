package com.giant.module.auth.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class UpdateAccountRequestDto(
    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    val password: String,

    @field:Pattern(
        regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$",
        message = "전화번호 형식이 올바르지 않습니다."
    )
    val phoneNumber: String?,

    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String?
)