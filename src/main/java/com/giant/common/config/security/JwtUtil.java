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
     * 필터를 위한 Token 추출
     */
    public String extractTokenForFilter(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;

        return Optional.of(header)
                .filter(h -> h.regionMatches(true, 0, "Bearer ", 0, 7))
                .map(h -> h.substring(7).trim())
                .filter(token -> !token.isEmpty())
                .orElse(null);
    }

    /**
     * Token 추출
     */
    public String extractToken(HttpServletRequest request) {
        return Optional.of(extractTokenForFilter(request))
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
    public void validateAccessToken(String token) {
        getClaims(token, false);
    }

    /**
     * AccessToken 에서 userId 추출
     */
    public long extractUserIdFromAccessToken(String token) {
        return Long.parseLong(getClaims(token, false).getSubject());
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
    public long extractUserIdFromRefreshToken(String token) {
        return Long.parseLong(getClaims(token, true).getSubject());
    }

    /**
     * RefreshToken 에서 expiration 추출
     */
    public long extractExpirationFromRefreshToken(String token) {
        return getClaims(token, true).getExpiration().getTime();
    }

}
