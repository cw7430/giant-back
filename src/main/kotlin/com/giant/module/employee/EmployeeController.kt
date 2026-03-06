package com.giant.module.employee

import com.giant.common.api.response.ResponseDto
import com.giant.common.api.response.SuccessResponseDto
import com.giant.module.employee.dto.request.EmployeeProfilesRequestDto
import io.swagger.v3.oas.annotations.Operation
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
    fun getEmployeeProfiles(@ModelAttribute @Valid requestDto: EmployeeProfilesRequestDto): ResponseEntity<ResponseDto> =
        ResponseEntity.ok(SuccessResponseDto.WithResult(employeeService.getEmployeeProfiles(requestDto)))
}