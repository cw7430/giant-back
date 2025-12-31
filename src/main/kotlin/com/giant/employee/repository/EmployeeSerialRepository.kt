package com.giant.employee.repository

import com.giant.employee.entity.EmployeeSerial
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeSerialRepository : JpaRepository<EmployeeSerial, Long> {
}