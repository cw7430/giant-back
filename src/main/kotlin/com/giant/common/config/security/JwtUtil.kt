package com.giant.common.config.security

import com.giant.common.api.exception.CustomException
import com.giant.common.api.type.ResponseCode
import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class JwtUtil(private val jwtProvider: JwtProvider) {
    /**
     * 필터를 위한 Token 추출
     */
    fun extractTokenForFilter(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization")
        if (header.isNullOrBlank()) return null

        if (!header.startsWith("Bearer ", ignoreCase = true)) return null

        val token = header.substring(7).trim()
        return token.takeIf { it.isNotEmpty() }
    }

    /**
     * Token 추출
     */
    fun extractToken(request: HttpServletRequest): String {
        return extractTokenForFilter(request)
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)
    }

    /**
     * Claim 추출
     */
    private fun getClaims(token: String, isRefresh: Boolean = false): Claims {
        return jwtProvider.parseClaims(token, isRefresh)
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)
    }

    /**
     * AccessToken 검증
     */
    fun validateAccessToken(token: String) {
        getClaims(token);
    }

    /**
     * AccessToken 에서 userId 추출
     */
    fun extractUserIdFromAccessToken(token: String): Long {
        return getClaims(token).subject.toLong()
    }

    /**
     * RefreshToken 에서 userId 추출
     */
    fun extractUserIdFromRefreshToken(token: String): Long {
        return getClaims(token, isRefresh = true).subject.toLong()
    }
}