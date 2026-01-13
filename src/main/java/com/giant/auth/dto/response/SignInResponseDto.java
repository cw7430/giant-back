package com.giant.auth.dto.response;

public record SignInResponseDto(
        Long expiresAt,
        String employeeCode,
        String employeeName,
        String accountRole,
        String employeeRole,
        String department,
        String team,
        String position
) {
}
