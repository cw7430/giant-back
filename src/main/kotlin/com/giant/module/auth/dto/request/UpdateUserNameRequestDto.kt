package com.giant.module.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

@Schema(name = "UpdateUserNameRequest")
data class UpdateUserNameRequestDto(
    @field:NotBlank(message = "아이디를 입력해주세요.")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{5,25}$",
        message = "아이디는 5자 이상 25자 이하, 영문 또는 영문, 숫자의 조합이어야 합니다."
    )
    @get:Schema(description = "아이디", example = "exampleid1234")
    val userName: String,

    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    @get:Schema(description = "비빌번호", example = "examplepw1234")
    val password: String,
)
