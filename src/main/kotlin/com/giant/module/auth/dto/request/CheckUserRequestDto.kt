package com.giant.module.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class CheckUserRequestDto (
    @NotBlank(message = "아이디를 입력해주세요.")
    var userName: String
)