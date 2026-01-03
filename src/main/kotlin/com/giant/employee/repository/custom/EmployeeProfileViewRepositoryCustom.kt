package com.giant.employee.repository.custom

import com.giant.employee.dto.response.EmployeeProfileResponseDto

interface EmployeeProfileViewRepositoryCustom {
    fun findAllByPageAndSize(page: Int, size: Int): List<EmployeeProfileResponseDto>
}