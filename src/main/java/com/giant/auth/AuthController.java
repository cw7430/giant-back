package com.giant.auth;

import com.giant.auth.doc.SignInSuccessResponseDoc;
import com.giant.auth.dto.request.RefreshRequestDto;
import com.giant.auth.dto.request.SignInRequestDto;
import com.giant.common.api.doc.error.ErrorResponseDoc;
import com.giant.common.api.doc.success.SuccessResponseDoc;
import com.giant.common.api.response.ResponseDto;
import com.giant.common.api.response.SuccessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "계정 API")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(
                    schema = @Schema(implementation = SignInSuccessResponseDoc.class)
            )),
            @ApiResponse(responseCode = "400", description = "입력값 오류", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDoc.BadRequest.class)
            )),
            @ApiResponse(responseCode = "401", description = "로그인 오류", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDoc.LoginError.class)
            )),
            @ApiResponse(responseCode = "500", description = "기타오류", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDoc.InternalServerError.class)
            ))
    })
    public ResponseEntity<ResponseDto> signIn(
            HttpServletResponse response,
            @RequestBody @Valid SignInRequestDto signInRequestDto
    ) {
        return ResponseEntity.ok(SuccessResponseDto.ok(authService.signIn(response, signInRequestDto)));
    }

    @PostMapping("/sign-out")
    @Operation(summary = "로그아웃")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content(
                    schema = @Schema(implementation = SuccessResponseDoc.class)
            )),
            @ApiResponse(responseCode = "500", description = "기타오류", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDoc.InternalServerError.class)
            ))
    })
    public ResponseEntity<ResponseDto> signOut(HttpServletResponse response) {
        authService.signOut(response);
        return ResponseEntity.ok(SuccessResponseDto.ok());
    }

    @PostMapping("/refresh")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공", content = @Content(
                    schema = @Schema(implementation = SignInSuccessResponseDoc.class)
            )),
            @ApiResponse(responseCode = "400", description = "입력값 오류", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDoc.BadRequest.class)
            )),
            @ApiResponse(responseCode = "401", description = "인증 오류", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDoc.Unauthorized.class)
            )),
            @ApiResponse(responseCode = "500", description = "기타오류", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDoc.InternalServerError.class)
            ))
    })
    @Operation(summary = "토큰 재발급")
    public ResponseEntity<ResponseDto> refreshAccessToken(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody @Valid RefreshRequestDto refreshRequestDto
    ) {
        return ResponseEntity.ok(SuccessResponseDto.ok(authService.refreshAccessToken(request, response, refreshRequestDto)));
    }
}
