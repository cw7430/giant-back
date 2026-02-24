package com.giant.common.config.security

import com.giant.common.api.exception.CustomException
import com.giant.common.api.type.ResponseCode
import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

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
     * AccessToken 에서 userId 추출
     */
    fun extractUserIdFromAccessToken(): Long {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw CustomException(ResponseCode.UNAUTHORIZED)
        }
        return authentication.name.toLongOrNull()
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)
    }

    /**
     * RefreshToken 에서 userId 추출
     */
    fun extractUserIdFromRefreshToken(): Long {
        val attributes = RequestContextHolder.currentRequestAttributes() as? ServletRequestAttributes
        val claims = attributes?.request?.getAttribute("refreshClaims") as? Claims
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)

        return claims.subject.toLongOrNull()
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)
    }
}