package com.giant.employee.repository

import com.giant.employee.entity.EmployeeRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRoleRepository : JpaRepository<EmployeeRole, Long> {
}