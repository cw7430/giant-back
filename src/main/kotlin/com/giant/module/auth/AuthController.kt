package com.giant.module.auth

import com.giant.common.api.doc.error.ErrorResponseDoc
import com.giant.common.api.doc.success.SuccessResponseDoc
import com.giant.common.api.response.ResponseDto
import com.giant.common.api.response.SuccessResponseDto
import com.giant.module.auth.doc.SignInSuccessResponseDoc
import com.giant.module.auth.dto.request.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "계정 API")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/sign-in")
    @Operation(summary = "로그인")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "로그인 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SignInSuccessResponseDoc::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400", description = "입력 값 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.BadRequest::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "401", description = "로그인 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.LoginError::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun signIn(@RequestBody @Valid requestDto: SignInRequestDto): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(authService.signIn(requestDto)))

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급")
    @SecurityRequirement(name = "RefreshToken")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "토큰 재발급 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SignInSuccessResponseDoc::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "401", description = "인증 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.Unauthorized::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun refresh(request: HttpServletRequest, @RequestBody requestDto: RefreshRequestDto): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(authService.refresh(request, requestDto)))

    @PostMapping("/sign-out")
    @Operation(summary = "로그아웃")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "로그아웃 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SuccessResponseDoc::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun signOut(@RequestBody requestDto: SignOutRequestDto): ResponseEntity<ResponseDto> {
        authService.signOut(requestDto)
        return ResponseEntity.ok(SuccessResponseDto.Simple)
    }

    @PostMapping("/check-user")
    @Operation(summary = "아이디 중복 체크")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "중복 체크 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SuccessResponseDoc::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400", description = "입력 값 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.BadRequest::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "409", description = "아이디 중복", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.DuplicateResource::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun checkUserDuplicate(@RequestBody @Valid requestDto: CheckUserRequestDto): ResponseEntity<ResponseDto> {
        authService.checkUserDuplicate(requestDto)
        return ResponseEntity.ok(SuccessResponseDto.Simple)
    }


    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    @SecurityRequirement(name = "AccessToken")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "비밀번호 변경 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SuccessResponseDoc::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400", description = "입력 값 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.BadRequest::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "401", description = "인증 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.Unauthorized::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "409", description = "비밀번호 중복", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.DuplicateResource::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun updatePassword(@RequestBody @Valid requestDto: UpdatePasswordRequestDto): ResponseEntity<ResponseDto> {
        authService.updatePassword(requestDto)
        return ResponseEntity.ok(SuccessResponseDto.Simple)
    }
}