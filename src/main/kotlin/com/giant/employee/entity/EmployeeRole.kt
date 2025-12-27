package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "employee_role", schema = "employee")
data class EmployeeRole(
    @Id
    @Column(name = "id")
    val employeeRoleId: Long,

    @Column(name = "role_name", nullable = false, unique = true, length = 25)
    val employeeRoleName: String,

    @OneToMany(mappedBy = "employeeRole", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val employees: MutableList<EmployeeProfile> = mutableListOf()
)
