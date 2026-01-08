package com.giant.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateAccountInfoRequestDto(
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,

        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{5,25}$",
                message = "아이디는 5자 이상 25자 이하, 영문 또는 영문, 숫자의 조합이어야 합니다."
        )
        String userName,

        @NotBlank(message = "휴대전화번호를 입력해주세요.")
        @Pattern(
                regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$",
                message = "전화번호 형식이 올바르지 않습니다."
        )
        String phoneNumber,

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email
) {
}
