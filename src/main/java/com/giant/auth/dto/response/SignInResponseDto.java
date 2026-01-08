package com.giant.auth.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponseDto(
        String accessToken,
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
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
