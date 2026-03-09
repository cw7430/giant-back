package com.giant.module.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

@Schema(name = "UpdatePasswordRequest")
data class UpdatePasswordRequestDto(
    @field:NotBlank(message = "기존 비밀번호를 입력해주세요.")
    @get:Schema(description = "기존 비밀번호", example = "examplepw1234")
    val prevPassword: String,

    @field:NotBlank(message = "새 비밀번호를 입력해주세요.")
    @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]|:;\"'<>,.?/~`]).{10,25}$",
        message = "비밀번호는 10자 이상 25자 이하이며, 영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
    )
    @get:Schema(description = "새 비밀번호", example = "examplepw4321")
    val newPassword: String
)
