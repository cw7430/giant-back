package com.giant.common.config.security;

import com.giant.common.api.exception.CustomException;
import com.giant.common.api.type.ResponseCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProvider jwtProvider;

    /**
     * Token 추출
     */
    public String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;

        return Optional.of(header)
                .filter(h -> h.regionMatches(true, 0, "Bearer ", 0, 7))
                .map(h -> h.substring(7).trim())
                .filter(token -> !token.isEmpty())
                .orElse(null);
    }

    /**
     * 추출 된 Token 검증
     */
    public String validationExtractedToken(HttpServletRequest request) {
        return Optional.of(extractBearerToken(request))
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
     * AccessToken 검증
     */
    public void validateAccessToken(HttpServletRequest request) {
        getClaims(validationExtractedToken(request), false);
    }

    /**
     * AccessToken 에서 userId 추출
     */
    public long extractUserIdFromAccessToken(HttpServletRequest request) {
        return Long.parseLong(getClaims(validationExtractedToken(request), false).getSubject());
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
        return Long.parseLong(getClaims(validationExtractedToken(request), true).getSubject());
    }

    /**
     * RefreshToken 에서 expiration 추출
     */
    public long extractExpirationFromRefreshToken(String token) {
        return getClaims(token, true).getExpiration().getTime();
    }

}
