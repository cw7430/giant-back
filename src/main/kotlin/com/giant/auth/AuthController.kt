package com.giant.auth

import com.giant.auth.dto.request.SignInRequestDto
import com.giant.common.api.response.ResponseDto
import com.giant.common.api.response.SuccessResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (private val authService: AuthService) {

    @PostMapping("/sign-in")
    fun signIn(@RequestBody signInRequestDto: SignInRequestDto): ResponseEntity<ResponseDto> {
        val signInInfo = authService.signIn(signInRequestDto)
        return ResponseEntity.ok(SuccessResponseDto.WithResult(signInInfo))
    }

}