package com.giant.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(
        @NotBlank(message = "아이디를 입력해주세요.")
        String userName,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,
        boolean isAuto
) {
}
