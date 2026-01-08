package com.giant.auth.dto;

public record SignInDto(
        Long accountId,
        String passwordHash,
        Long accountRoleId,
        String accountRoleName,
        String employeeCode,
        String employeeName,
        String employeeRoleName,
        String departmentName,
        String teamName,
        String positionName
) {
}
