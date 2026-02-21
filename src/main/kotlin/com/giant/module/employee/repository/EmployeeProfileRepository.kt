package com.giant.module.employee.repository

import com.giant.module.employee.entity.EmployeeProfile
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeProfileRepository : JpaRepository<EmployeeProfile, Long>