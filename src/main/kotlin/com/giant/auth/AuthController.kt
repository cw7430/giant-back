package com.giant.auth

import com.giant.auth.dto.request.SignInRequestDto
import com.giant.auth.dto.response.RefreshRequestDto
import com.giant.common.api.response.ErrorResponseDto
import com.giant.common.api.response.ResponseDto
import com.giant.common.api.response.SuccessResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (private val authService: AuthService) {


    @PostMapping("/sign-in")
    @Operation(summary = "로그인")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "로그인 성공"),
        ApiResponse(
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDto.Simple::class)
                )
            ]
        )
    )
    fun signIn(
        response: HttpServletResponse,
        @RequestBody @Valid signInRequestDto: SignInRequestDto
    ): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(authService.signIn(response, signInRequestDto)))

    @PostMapping("/sign-out")
    @Operation(summary = "로그아웃")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "로그아웃 성공"),
        ApiResponse(
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDto.Simple::class)
                )
            ]
        )
    )
    fun signOut(response: HttpServletResponse): ResponseEntity<ResponseDto> {
        authService.signOut(response)
        return ResponseEntity.ok(SuccessResponseDto.Simple)
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "재발급 성공"),
        ApiResponse(
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDto.Simple::class)
                )
            ]
        )
    )
    fun refreshAccessToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody refreshRequestDto: RefreshRequestDto
    ): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(authService.refreshAccessToken(request, response, refreshRequestDto)))

}