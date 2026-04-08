package com.giant.module.employee.repository

import com.giant.module.employee.entity.Department
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository : JpaRepository<Department, Long> {
    @EntityGraph(attributePaths = ["_teams"])
    override fun findAll(): List<Department>
}