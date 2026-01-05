package com.giant.common.config.security;

import com.giant.common.api.exception.CustomException;
import com.giant.common.api.type.ResponseCode;
import com.giant.common.util.CookieUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;

    /**
     * AccessToken 추출
     */
    private String extractAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        return Optional.of(header)
                .filter(h -> h.regionMatches(true, 0, "Bearer ", 0, 7))
                .map(h -> h.substring(7).trim())
                .filter(token -> !token.isEmpty())
                .orElseThrow(() -> new CustomException(ResponseCode.UNAUTHORIZED));
    }

    /**
     * RefreshToken 추출
     */
    private String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }
        return Arrays.stream(cookies)
                .filter(c -> "refreshToken".equals(c.getName()))
                .map(Cookie::getValue)
                .filter(token -> !token.isBlank())
                .findFirst()
                .orElseThrow(() -> new CustomException(ResponseCode.UNAUTHORIZED));
    }

    /**
     * Claim 추출
     */
    private Claims getClaims(String token, boolean isRefresh) {
        Claims claims = jwtProvider.parseClaims(token, isRefresh);
        if (claims == null || claims.isEmpty()) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }
        return claims;
    }

    /**
     * RefreshToken 쿠키 등록
     */
    public void addRefreshTokenToCookie(
            HttpServletResponse response,
            String refreshToken,
            boolean isAuto
    ) {
        long maxAgeInSeconds = -1;
        if (isAuto) {
            long diffMillis =
                    extractExpirationFromRefreshToken(refreshToken)
                            - System.currentTimeMillis();

            maxAgeInSeconds = Math.max(0, diffMillis / 1000);
        }
        cookieUtil.addCookie(response, "refreshToken", refreshToken, maxAgeInSeconds, true);
    }

    /**
     * RefreshToken 쿠키 제거
     */
    public void removeRefreshTokenCookie(HttpServletResponse response) {
        cookieUtil.removeCookie(response, "refreshToken", true);
    }

    /**
     * AccessToken 검증
     */
    public void validateAccessToken(HttpServletRequest request) {
        getClaims(extractAccessToken(request), false);
    }

    /**
     * AccessToken 에서 userId 추출
     */
    public long extractUserIdFromAccessToken(HttpServletRequest request) {
        return Long.parseLong(getClaims(extractAccessToken(request), false).getId());
    }

    /**
     * AccessToken 에서 expiration 추출
     */
    public long extractExpirationFromAccessToken(String token) {
        return getClaims(token, false).getExpiration().getTime();
    }

    /**
     * RefreshToken 에서 userId 추출
     */
    public long extractUserIdFromRefreshToken(HttpServletRequest request) {
        return Long.parseLong(getClaims(extractRefreshToken(request), true).getId());
    }

    /**
     * RefreshToken 에서 expiration 추출
     */
    public long extractExpirationFromRefreshToken(String token) {
        return getClaims(token, true).getExpiration().getTime();
    }

}
