package com.giant.employee.repository

import com.giant.employee.entity.Department
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository : JpaRepository<Department, Long> {
}