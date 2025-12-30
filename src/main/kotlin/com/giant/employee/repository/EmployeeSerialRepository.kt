package com.giant.employee.repository

import com.giant.employee.entity.EmployeeSerial
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeSerialRepository : JpaRepository<EmployeeSerial, Long> {
}