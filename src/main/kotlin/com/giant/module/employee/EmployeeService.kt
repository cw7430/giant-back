package com.giant.module.employee

import com.giant.common.api.exception.CustomException
import com.giant.common.api.response.PageResponse
import com.giant.common.api.type.ResponseCode
import com.giant.common.config.security.JwtUtil
import com.giant.module.employee.dto.request.EmployeeProfilesRequestDto
import com.giant.module.employee.dto.response.EmployeeProfileResponseDto
import com.giant.module.employee.dto.response.PositionResponseDto
import com.giant.module.employee.repository.EmployeeProfileViewRepository
import com.giant.module.employee.repository.PositionRepository
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    private val employeeProfileViewRepository: EmployeeProfileViewRepository,
    private val positionRepository: PositionRepository,
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

    fun getEmployeeProfile(id: Long): EmployeeProfileResponseDto {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val employeeProfile = employeeProfileViewRepository.findByIdOrNull(id)
            ?: throw CustomException(ResponseCode.RESOURCE_NOT_FOUND)
        log.info { "Employee Profile requested by account ID: $accountId" }
        log.info { "employee ID: $id" }
        return EmployeeProfileResponseDto.from(employeeProfile)
    }

    fun getPositions(): List<PositionResponseDto> {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val positions = positionRepository.findAll()
        log.info { "Position requested by account ID: $accountId" }

        return positions.map { position ->
            PositionResponseDto.from(position)
        }
    }
}