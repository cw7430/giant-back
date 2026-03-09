package com.giant.module.employee

import com.giant.common.api.doc.error.ErrorResponseDoc
import com.giant.common.api.response.ResponseDto
import com.giant.common.api.response.SuccessResponseDto
import com.giant.module.employee.doc.EmployeeProfilesResponseDoc
import com.giant.module.employee.dto.request.EmployeeProfilesRequestDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/hr")
@Tag(name = "Employee Controller", description = "인사 API")
class EmployeeController(
    private val employeeService: EmployeeService
) {
    @GetMapping("/profiles")
    @Operation(summary = "직원목록")
    @SecurityRequirement(name = "AccessToken")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "직원 목록 조회 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = EmployeeProfilesResponseDoc::class)
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
            responseCode = "500", description = "기타오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun getEmployeeProfiles(@ModelAttribute @Valid requestDto: EmployeeProfilesRequestDto): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(employeeService.getEmployeeProfiles(requestDto)))
}