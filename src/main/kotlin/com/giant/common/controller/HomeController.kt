package com.giant.common.controller

import com.giant.common.api.response.ErrorResponseDto
import com.giant.common.api.response.ResponseDto
import com.giant.common.api.response.SuccessResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Home Controller", description = "공통 API")
class HomeController {

    @GetMapping("/health-check")
    @Operation(summary = "헬스 체크")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 성공"),
        ApiResponse(
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDto.Simple::class)
                )
            ]
        )
    )
    fun healthCheck(): ResponseEntity<ResponseDto> {
        return ResponseEntity.ok(SuccessResponseDto.Simple)
    }
}