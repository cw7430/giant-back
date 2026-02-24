package com.giant.common.api.exception

import com.giant.common.api.response.ErrorResponseDto
import com.giant.common.api.type.ResponseCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class CustomAccessDeniedHandler(private val objectMapper: ObjectMapper) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val errorResponse = ErrorResponseDto.Simple(
            ResponseCode.FORBIDDEN.code,
            ResponseCode.FORBIDDEN.message
        )
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}