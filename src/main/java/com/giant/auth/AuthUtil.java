package com.giant.auth;

import com.giant.auth.dto.SignInDto;
import com.giant.auth.dto.response.SignInResponseDto;
import com.giant.common.api.exception.CustomException;
import com.giant.common.api.type.ResponseCode;
import com.giant.common.config.security.JwtProvider;
import com.giant.common.config.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;

    public void validateAccountRole(SignInDto info) {
        if (info.accountRoleId() == 3L) {
            throw new CustomException(ResponseCode.FORBIDDEN);
        }
    }

    public String generateAndSetTokens(HttpServletResponse response, SignInDto info, boolean isAuto) {
        String accessToken = jwtProvider.generateAccessToken(info.accountId(), info.accountRoleId());
        String refreshToken = jwtProvider.generateRefreshToken(info.accountId(), isAuto);

        jwtUtil.addAccessTokenToCookie(response, accessToken);
        jwtUtil.addRefreshTokenToCookie(response, refreshToken, isAuto);
        return accessToken;
    }

    public SignInResponseDto createSignInResponse(SignInDto info, String accessToken) {
        Long expiresAt = jwtUtil.extractExpirationFromAccessToken(accessToken);
        return new SignInResponseDto(
                expiresAt, info.employeeCode(),
                info.employeeName(), info.accountRoleName(),
                info.employeeName(), info.departmentName(),
                info.teamName(), info.positionName()
        );
    }
}
