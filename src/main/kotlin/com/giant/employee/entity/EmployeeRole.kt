package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "employee_role", schema = "employee")
class EmployeeRole(
    @Id
    @Column(name = "id", nullable = false)
    var employeeRoleId: Long = 0,

    @Column(
        name = "role_name",
        nullable = false,
        unique = true,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    var employeeRoleName: String = "",

    @OneToMany(mappedBy = "employeeRole", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val employees: MutableList<EmployeeProfile> = mutableListOf()
)
