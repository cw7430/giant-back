package com.giant.auth

import com.giant.auth.dto.request.SignInRequestDto
import com.giant.common.api.response.ResponseDto
import com.giant.common.api.response.SuccessResponseDto
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (private val authService: AuthService) {

    @PostMapping("/sign-in")
    fun signIn(
        response: HttpServletResponse,
        @RequestBody @Valid signInRequestDto: SignInRequestDto
    ): ResponseEntity<ResponseDto> {
        val signInInfo = authService.signIn(response, signInRequestDto)
        return ResponseEntity.ok(SuccessResponseDto.WithResult(signInInfo))
    }

}