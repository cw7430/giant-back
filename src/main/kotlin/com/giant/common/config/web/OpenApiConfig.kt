package com.giant.common.config.web

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenAPI(): OpenAPI? {
        val bearerScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION)

        return OpenAPI()
            .components(Components().addSecuritySchemes("BearerAuth", bearerScheme))
            .addSecurityItem(SecurityRequirement().addList("BearerAuth"))
    }
}