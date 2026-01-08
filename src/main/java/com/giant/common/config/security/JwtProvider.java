package com.giant.common.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    private final SecretKey accessSecretKey;
    private final long accessTokenExpireTime;
    private final SecretKey refreshSecretKey;
    private final long refreshTokenExpireTime;

    public JwtProvider(
            @Value("${jwt.access.secret}") String accessSecretKey,
            @Value("${jwt.access.expiration}") Duration accessTokenExpireTime,
            @Value("${jwt.refresh.secret}") String refreshSecretKey,
            @Value("${jwt.refresh.expiration}") Duration refreshTokenExpireTime
    ) {
        this.accessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretKey));
        this.accessTokenExpireTime = accessTokenExpireTime.toMillis();
        this.refreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretKey));
        this.refreshTokenExpireTime = refreshTokenExpireTime.toMillis();
    }

    /**
     * AccessToken 생성
     */
    public String generateAccessToken(Long id, Long accountRoleId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenExpireTime);

        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("accountRole", String.valueOf(accountRoleId))
                .issuedAt(now)
                .expiration(expiry)
                .signWith(accessSecretKey, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * RefreshToken 생성
     */
    public String generateRefreshToken(Long id, boolean isAuto) {
        Date now = new Date();
        long refreshTokenExpireTime = isAuto ? Duration.ofDays(365).toMillis() : this.refreshTokenExpireTime;
        Date expiry = new Date(now.getTime() + refreshTokenExpireTime);

        return Jwts.builder()
                .subject(String.valueOf(id))
                .issuedAt(now)
                .expiration(expiry)
                .signWith(refreshSecretKey, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Claim 변환
     */
    public Claims parseClaims(String token, boolean isRefresh) {
        SecretKey key = isRefresh ? this.refreshSecretKey : this.accessSecretKey;
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.debug("JWT expired");
        } catch (JwtException e) {
            log.warn("Invalid JWT", e);
        }
        return null;
    }
}
