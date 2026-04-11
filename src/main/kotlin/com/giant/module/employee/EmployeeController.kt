package com.giant.module.employee

import com.giant.common.api.doc.error.ErrorResponseDoc
import com.giant.common.api.response.ResponseDto
import com.giant.common.api.response.SuccessResponseDto
import com.giant.module.employee.doc.DepartmentsResponseDoc
import com.giant.module.employee.doc.EmployeeCodeResponseDoc
import com.giant.module.employee.doc.EmployeeProfileResponseDoc
import com.giant.module.employee.doc.EmployeeProfilesResponseDoc
import com.giant.module.employee.doc.PositionsResponseDoc
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
import org.springframework.web.bind.annotation.*

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

    @GetMapping("/profiles/{id}")
    @Operation(summary = "직원 상세 정보")
    @SecurityRequirement(name = "AccessToken")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "직원 상세 정보 조회 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = EmployeeProfileResponseDoc::class)
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
            responseCode = "404", description = "리소스 미 반환", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.ResourceNotFound::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500", description = "기타 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun getEmployeeProfile(@PathVariable id: Long): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(employeeService.getEmployeeProfile(id)))


    @GetMapping("/positions")
    @Operation(summary = "직급 목록")
    @SecurityRequirement(name = "AccessToken")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "직급 목록 조회 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PositionsResponseDoc::class)
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
            responseCode = "500", description = "기타 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun getPositions(): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(employeeService.getPositions()))

    @GetMapping("/departments")
    @Operation(summary = "부서 목록")
    @SecurityRequirement(name = "AccessToken")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "부서 목록 조회 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = DepartmentsResponseDoc::class)
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
            responseCode = "500", description = "기타 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun getDepartments(): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(employeeService.getDepartments()))

    @GetMapping("/employee-code")
    @Operation(summary = "사번 조회")
    @SecurityRequirement(name = "AccessToken")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "사번 조회 성공", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = EmployeeCodeResponseDoc::class)
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
            responseCode = "500", description = "기타 오류", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDoc.InternalServerError::class)
                )
            ]
        )
    )
    fun getEmployeeCode(): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(employeeService.getEmployeeCode()))
}