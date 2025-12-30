package com.giant.common.config.security

import com.giant.common.config.security.constant.ClaimElement
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
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)

        if (token != null) {
            runCatching {
                val claims = jwtProvider.parseClaims(token) ?: return@runCatching

                val userId = claims.subject
                val roleCode = claims.get(ClaimElement.ACCOUNT_ROLE.element, String::class.java)
                val role = Role.fromCode(roleCode)

                val authorities = listOf(SimpleGrantedAuthority(role.authority))

                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(userId, null, authorities)
                
                log.debug { "Authenticated user: $userId (${role.authority})" }
            }.onFailure { e ->
                log.warn { "JWT Authentication failed: ${e.message}" }
                SecurityContextHolder.clearContext()
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ", ignoreCase = true) }
            ?.substring(7)
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
    }
}