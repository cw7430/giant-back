package com.giant.module.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class SignInRequestDto(
    @NotBlank(message = "아이디를 입력해주세요.")
    val userName: String,

    @NotBlank(message = "비밀번호를 입력해주세요.")
    val password: String,

    val isAuto: Boolean = false
)