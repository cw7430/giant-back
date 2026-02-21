package com.giant.common.config.security

import com.giant.common.config.security.type.Role
import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(private val jwtProvider: JwtProvider, private val jwtUtil: JwtUtil) :
    OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = jwtUtil.extractTokenForFilter(request)
        if (token == null) {
            filterChain.doFilter(request, response)
            return
        }

        val isRefreshPath = request.requestURI == "/api/v1/auth/refresh"
        val claims = jwtProvider.parseClaims(token, isRefresh = isRefreshPath)

        if (claims != null) {
            if (isRefreshPath) {
                request.setAttribute("refreshClaims", claims)
            } else {
                setAuthentication(claims)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun setAuthentication(claims: Claims) {
        val id = claims.subject
        val roleCode = claims.get("accountRole", String::class.java)
        val role = Role.fromCode(roleCode)
        val authorities = listOf(SimpleGrantedAuthority(role.authority))

        val authentication =
            UsernamePasswordAuthenticationToken(id, null, authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }
}
