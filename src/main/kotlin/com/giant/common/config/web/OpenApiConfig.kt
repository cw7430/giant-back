package com.giant.common.config.web

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
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
        val accessTokenScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION)
            .description("Access Token을 입력하세요 (Bearer 제외하고 입력)")

        val refreshTokenScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION)
            .description("Refresh Token을 입력하세요 (Bearer 제외하고 입력)")

        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes("AccessToken", accessTokenScheme)
                    .addSecuritySchemes("RefreshToken", refreshTokenScheme)
            )
    }
}