package com.giant.auth.dto.request;

import com.giant.common.annotation.EqualTo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@EqualTo(
        field = "newPassword",
        equalTo = "confirmPassword",
        message = "새로운 비밀번호가 일치하지 않습니다."
)
public record UpdatePasswordRequestDto(
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String prevPassword,

        @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]|:;\"'<>,.?/~`]).{10,25}$",
                message = "비밀번호는 10자 이상 25자 이하이며, 영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
        )
        String newPassword,

        @NotBlank(message = "비밀번호 확인을 입력해주세요.")
        String confirmPassword
) {
}
