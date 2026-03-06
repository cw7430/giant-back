package com.giant.module.employee

import com.giant.common.config.security.JwtUtil
import com.giant.module.employee.repository.EmployeeProfileViewRepository

class EmployeeService(
    private val employeeProfileViewRepository: EmployeeProfileViewRepository,
    private val jwtUtil: JwtUtil,
) {
//    fun getEmployeeProfiles () {
//        return employeeProfileViewRepository.findAll()
//    }
}