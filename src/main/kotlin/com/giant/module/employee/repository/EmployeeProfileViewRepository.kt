package com.giant.module.employee.repository

import com.giant.module.employee.entity.EmployeeProfileView
import com.giant.module.employee.repository.custom.EmployeeProfileViewRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeProfileViewRepository : JpaRepository<EmployeeProfileView, Long>, EmployeeProfileViewRepositoryCustom