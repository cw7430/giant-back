package com.giant.auth;

import com.giant.auth.dto.SignInDto;
import com.giant.auth.dto.response.SignInResponseDto;
import com.giant.common.api.exception.CustomException;
import com.giant.common.api.type.ResponseCode;
import com.giant.common.config.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final JwtUtil jwtUtil;

    /* ---------- validation ---------- */

    public void validateAccountRole(SignInDto info) {
        if (info.accountRoleId() == 3L) {
            throw new CustomException(ResponseCode.FORBIDDEN);
        }
    }

    /* ---------- expiration ---------- */

    public Instant extractRefreshTokenExpiresAt(String refreshToken) {
        long expiresAtMillis = jwtUtil.extractExpirationFromRefreshToken(refreshToken);
        return Instant.ofEpochMilli(expiresAtMillis);
    }

    public long extractAccessTokenExpiresAt(String accessToken) {
        return jwtUtil.extractExpirationFromAccessToken(accessToken);
    }

    /* ---------- response builder ---------- */

    public SignInResponseDto buildSignInResponse(
            SignInDto info,
            String accessToken,
            String refreshToken,
            boolean isAuto
    ) {
        return new SignInResponseDto(
                accessToken,
                extractAccessTokenExpiresAt(accessToken),
                refreshToken,
                jwtUtil.extractExpirationFromRefreshToken(refreshToken),
                isAuto,
                info.employeeCode(),
                info.employeeName(),
                info.accountRoleName(),
                info.employeeName(),
                info.departmentName(),
                info.teamName(),
                info.positionName()
        );
    }
}
