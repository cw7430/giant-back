package com.giant.employee.repository

import com.giant.employee.entity.EmployeeProfileView
import com.giant.employee.repository.custom.EmployeeProfileViewRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeProfileViewRepository : JpaRepository<EmployeeProfileView, Long>,
    EmployeeProfileViewRepositoryCustom {
}