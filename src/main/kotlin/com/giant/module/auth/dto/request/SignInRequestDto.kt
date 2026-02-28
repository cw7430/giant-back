package com.giant.module.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class SignInRequestDto(
    @field:NotBlank(message = "아이디를 입력해주세요.")
    val userName: String,

    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    val password: String,

    val isAuto: Boolean = false
)