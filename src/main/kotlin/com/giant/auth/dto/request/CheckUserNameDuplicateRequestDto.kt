package com.giant.auth.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CheckUserNameDuplicateRequestDto (
    @field:NotBlank(message = "아이디를 입력해주세요.")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{5,25}$",
        message = "아이디는 5자 이상 25자 이하, 영문 또는 영문, 숫자의 조합이어야 합니다."
    )
    val userName: String,
)