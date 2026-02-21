package com.giant.module.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "position", schema = "employee")
class Position(
    @Id
    @Column(name = "id", nullable = false)
    var positionId: Long,

    @Column(name = "position_name", nullable = false, unique = true)
    var positionName: String,

    @Column(name = "basic_salary", nullable = false)
    var basicSalary: Long,

    @Column(name = "incentive_salary", nullable = false)
    var incentiveSalary: Long,
) {
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private val _employeeProfiles: MutableList<EmployeeProfile> = mutableListOf()

    val employeeProfiles: List<EmployeeProfile>
        get() = _employeeProfiles.toList()
}
