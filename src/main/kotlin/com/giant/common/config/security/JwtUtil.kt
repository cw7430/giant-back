package com.giant.common.config.security

import com.giant.common.api.code.ResponseCode
import com.giant.common.api.exception.CustomException
import com.giant.common.config.security.constant.ClaimElement
import com.giant.common.config.security.dto.ClaimDto
import jakarta.servlet.http.HttpServletRequest
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
     * AccessToken 검증
     */
    fun validateAccessToken(request: HttpServletRequest) {
        jwtProvider.getClaims(extractAccessToken(request))
    }


    /**
     * AccessToken 에서 claim 추출
     */
    fun extractClaimsFromAccessToken(request: HttpServletRequest): ClaimDto {
        val token = extractAccessToken(request)
        val claims = jwtProvider.getClaims(token)

        return ClaimDto(
            userId = claims.subject,
            accountRole = claims.get(ClaimElement.ACCOUNT_ROLE.element, String::class.java),
            employeeRole = claims.get(ClaimElement.EMPLOYEE_ROLE.element, String::class.java)
        )
    }

    /**
     * RefreshToken 에서 userId 추출
     */
    fun extractUserIdFromRefreshToken(request: HttpServletRequest): String =
        jwtProvider.getClaims(extractRefreshToken(request), true).subject
}