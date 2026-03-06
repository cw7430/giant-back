package com.giant.module.employee.repository.custom

import com.giant.module.employee.dto.request.EmployeeProfilesRequestDto
import com.giant.module.employee.dto.response.EmployeeProfileResponseDto

interface EmployeeProfileViewRepositoryCustom {
    fun findEmployeeProfiles(requestDto: EmployeeProfilesRequestDto): List<EmployeeProfileResponseDto>
    fun countEmployeeProfiles(): Long
}