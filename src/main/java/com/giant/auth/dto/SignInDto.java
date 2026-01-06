package com.giant.auth.dto;

public record SignInDto(
        Long accountId,
        String passwordHash,
        Long accountRoleId,
        String accountRoleName
) {
}
