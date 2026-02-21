package com.giant.common.config.security

import com.giant.common.config.security.type.TokenResponseClaim
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.access.secret}") accessSecretKey: String,
    @Value("\${jwt.access.expiration}") accessTokenExpireTime: Duration,
    @Value("\${jwt.refresh.secret}") refreshSecretKey: String,
    @Value("\${jwt.refresh.expiration}") refreshTokenExpireTime: Duration
) {
    private val accessSecretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretKey))
    private val accessTokenExpireTime: Long = accessTokenExpireTime.toMillis()
    private val refreshSecretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretKey))
    private val refreshTokenExpireTime: Long = refreshTokenExpireTime.toMillis()

    private val log = KotlinLogging.logger {}

    /**
     * AccessToken 생성
     */
    fun generateAccessToken(userId: String, accountRole: String): TokenResponseClaim {
        val now = Date()
        val expiry = Date(now.time + accessTokenExpireTime)

        val token = Jwts.builder()
            .subject(userId)
            .claim("accountRole", accountRole)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(accessSecretKey, Jwts.SIG.HS256)
            .compact()

        return TokenResponseClaim(
            token = token,
            expiresAtMs = expiry.time
        )
    }

    /**
     * RefreshToken 생성
     */
    fun generateRefreshToken(userId: String, isAuto: Boolean = false): TokenResponseClaim {
        val now = Date()
        val expiry = when (isAuto) {
            true -> Date(now.time + Duration.ofDays(365).toMillis())
            false -> Date(now.time + refreshTokenExpireTime)
        }

        val token = Jwts.builder()
            .subject(userId)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(refreshSecretKey, Jwts.SIG.HS256)
            .compact()

        return TokenResponseClaim(
            token = token,
            expiresAtMs = expiry.time
        )
    }

    /**
     * Claim 변환
     */
    fun parseClaims(token: String, isRefresh: Boolean = false): Claims? {
        val key: SecretKey = if (isRefresh) refreshSecretKey else accessSecretKey

        return try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredJwtException) {
            log.debug("JWT expired")
            null
        } catch (e: JwtException) {
            log.warn("Invalid JWT", e)
            null
        }
    }
}