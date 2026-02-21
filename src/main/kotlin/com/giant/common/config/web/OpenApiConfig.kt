package com.giant.common.config.web

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders

@Configuration
@Profile("!production")
class OpenApiConfig {
    @Bean
    fun customOpenAPI(): OpenAPI? {
        val bearerScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("Bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION)

        return OpenAPI()
            .components(Components().addSecuritySchemes("BearerAuth", bearerScheme))
            .addSecurityItem(SecurityRequirement().addList("AccessToken"))
            .addSecurityItem(SecurityRequirement().addList("RefreshToken"))
    }
}