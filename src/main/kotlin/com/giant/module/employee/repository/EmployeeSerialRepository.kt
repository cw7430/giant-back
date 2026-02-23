package com.giant.module.employee.repository

import com.giant.module.employee.entity.EmployeeSerial
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeSerialRepository : JpaRepository<EmployeeSerial, Long>