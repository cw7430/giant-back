package com.giant.module.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "position", schema = "employee")
class Position(
    @Id
    @Column(name = "id", nullable = false)
    val positionId: Long? = null,

    @Column(name = "position_name", nullable = false, unique = true)
    var positionName: String? = null,

    @Column(name = "basic_salary", nullable = false)
    var basicSalary: Long? = null,

    @Column(name = "incentive_salary", nullable = false)
    var incentiveSalary: Long? = null,

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    var employeeProfiles: MutableList<EmployeeProfile> = mutableListOf()
) {
}
