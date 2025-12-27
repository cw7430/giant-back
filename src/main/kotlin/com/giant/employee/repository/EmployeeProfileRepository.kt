package com.giant.employee.repository

import com.giant.employee.entity.EmployeeProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeProfileRepository : JpaRepository<EmployeeProfile, Long> {
}