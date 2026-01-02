package com.giant.common.config.security

import com.giant.common.api.code.ResponseCode
import com.giant.common.api.exception.CustomException
import com.giant.common.util.addCookie
import com.giant.common.util.removeCookie
import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class JwtUtil(private val jwtProvider: JwtProvider) {
    /**
     * AccessToken 추출
     */
    private fun extractAccessToken(request: HttpServletRequest): String {
        val token = request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)

        if (token == null) {
            throw CustomException(ResponseCode.UNAUTHORIZED)
        }

        return token
    }

    /**
     * RefreshToken 추출
     */
    private fun extractRefreshToken(request: HttpServletRequest): String {
        val token = request.cookies
            ?.firstOrNull { it.name == "refreshToken" }
            ?.value
        if (token == null) {
            throw CustomException(ResponseCode.UNAUTHORIZED)
        }
        return token
    }

    /**
     * RefreshToken 쿠키 등록
     */
    fun addRefreshTokenToCookie(
        response: HttpServletResponse,
        refreshToken: String,
        isAuto: Boolean,
    ) {
        val maxAgeInSeconds =
            if (isAuto) {
                ((extractExpirationFromRefreshToken(refreshToken)
                        - System.currentTimeMillis()) / 1000)
                    .coerceAtLeast(0)
            } else {
                -1
            }
        addCookie(
            response,
            "refreshToken",
            refreshToken,
            true,
            maxAgeInSeconds
        )
    }

    /**
     * RefreshToken 쿠키 제거
     */
    fun removeRefreshTokenFromCookie(response: HttpServletResponse) {
        removeCookie(response, "refreshToken", true)
    }

    /**
     * Claim 추출
     */
    private fun getClaims(token: String, isRefresh: Boolean = false): Claims =
        jwtProvider.parseClaims(token, isRefresh)
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)

    /**
     * AccessToken 검증
     */
    fun validateAccessToken(request: HttpServletRequest) {
        getClaims(extractAccessToken(request))
    }

    /**
     * AccessToken 에서 userId 추출
     */
    fun extractUserIdFromAccessToken(request: HttpServletRequest): String =
        getClaims(extractAccessToken(request)).subject

    /**
     * AccessToken 에서 expiration 추출
     */
    fun extractExpirationFromAccessToken(token: String): Long =
        getClaims(token).expiration.time

    /**
     * RefreshToken 에서 userId 추출
     */
    fun extractUserIdFromRefreshToken(request: HttpServletRequest): String =
        getClaims(extractRefreshToken(request), true).subject

    /**
     * RefreshToken 에서 expiration 추출
     */
    fun extractExpirationFromRefreshToken(token: String): Long =
        getClaims(token, true).expiration.time
}