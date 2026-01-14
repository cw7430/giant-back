package com.giant.auth.dto.response;

public record SignInResponseDto(
        String accessToken,
        Long accessTokenExpiresAt,
        String refreshToken,
        Long refreshTokenExpiresAt,
        boolean isAuto,
        String employeeCode,
        String employeeName,
        String accountRole,
        String employeeRole,
        String department,
        String team,
        String position
) {
}
