package com.giant.common.config.security

import com.giant.common.config.security.constant.Role
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val claims = jwtUtil.extractClaimsFromAccessToken(request)
        val userId = claims.userId
        val roleCode = claims.accountRole
        val role = Role.fromCode(roleCode)

        val authorities = listOf(SimpleGrantedAuthority(role.authority))
        log.debug { "Authenticated user: $userId (${role.authority})" }
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(userId, null, authorities)


        filterChain.doFilter(request, response)
    }
}