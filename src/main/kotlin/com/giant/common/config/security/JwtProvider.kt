package com.giant.common.config.security

import com.giant.common.config.security.constant.ClaimElement
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
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

    /**
     * AccessToken 생성
     */
    fun generateAccessToken(userId: String, accountRole: String): String {
        val now = Date()
        val expiry = Date(now.time + accessTokenExpireTime)

        return Jwts.builder()
            .subject(userId)
            .claim(ClaimElement.ACCOUNT_ROLE.element, accountRole)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(accessSecretKey, Jwts.SIG.HS256)
            .compact()
    }

    /**
     * RefreshToken 생성
     */
    fun generateRefreshToken(userId: String, isAuto: Boolean = false): String {
        val now = Date()
        val expiry = when (isAuto) {
            true -> Date(now.time + Duration.ofDays(365).toMillis())
            false -> Date(now.time + refreshTokenExpireTime)
        }

        return Jwts.builder()
            .subject(userId)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(refreshSecretKey, Jwts.SIG.HS256)
            .compact()
    }

    /**
     * Claim 변환
     */
    fun parseClaims(token: String, isRefresh: Boolean = false): Claims? =
        runCatching {
            val key = if (isRefresh) refreshSecretKey else accessSecretKey
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        }.getOrNull()

}