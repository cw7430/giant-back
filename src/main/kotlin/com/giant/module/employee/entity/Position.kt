package com.giant.module.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "\"position\"", schema = "employee")
class Position(
    @Column(name = "position_code", nullable = false, unique = true)
    var positionCode: String,

    @Column(name = "position_name", nullable = false, unique = true)
    var positionName: String,

    @Column(name = "basic_salary", nullable = false)
    var basicSalary: Long,

    @Column(name = "incentive_salary", nullable = false)
    var incentiveSalary: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val positionId: Long? = null

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private val _employeeProfiles: MutableList<EmployeeProfile> = mutableListOf()

    val employeeProfiles: List<EmployeeProfile>
        get() = _employeeProfiles.toList()

    companion object {
        fun create(
            positionCode: String,
            positionName: String,
            basicSalary: Long,
            incentiveSalary: Long
        ) = Position(positionCode, positionName, basicSalary, incentiveSalary)
    }
}
