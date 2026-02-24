package com.giant.module.employee.repository

import com.giant.module.auth.entity.Account
import com.giant.module.employee.entity.EmployeeProfile
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeProfileRepository : JpaRepository<EmployeeProfile, Long> {
    fun findByAccount(account: Account): EmployeeProfile?
}