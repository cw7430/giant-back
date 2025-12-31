package com.giant.employee.repository

import com.giant.employee.entity.EmployeeRole
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRoleRepository : JpaRepository<EmployeeRole, Long> {
}