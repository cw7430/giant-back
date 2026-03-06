package com.giant.module.employee

import com.giant.common.api.response.PageResponse
import com.giant.common.config.security.JwtUtil
import com.giant.module.employee.dto.request.EmployeeProfilesRequestDto
import com.giant.module.employee.dto.response.EmployeeProfileResponseDto
import com.giant.module.employee.repository.EmployeeProfileViewRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    private val employeeProfileViewRepository: EmployeeProfileViewRepository,
    private val jwtUtil: JwtUtil,
) {
    private val log = KotlinLogging.logger {}

    fun getEmployeeProfiles(requestDto: EmployeeProfilesRequestDto): PageResponse<EmployeeProfileResponseDto> {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val employeeProfiles = employeeProfileViewRepository.findEmployeeProfiles(requestDto)
        val totalElements = employeeProfileViewRepository.countEmployeeProfiles()
        log.info { "Employee Profiles requested by account ID: $accountId" }
        return PageResponse.of(
            content = employeeProfiles,
            pageRequest = requestDto,
            totalElements
        )
    }
}