package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "position", schema = "employee")
data class Position(
    @Id
    @Column(name = "id")
    val positionId: Long,

    @Column(name = "position_name", nullable = false, unique = true, length = 255)
    val positionName: String,

    @Column(name= "basic_salary", nullable = false)
    val basicSalary: Long,

    @Column(name= "incentive_salary", nullable = false)
    val incentiveSalary: Long,

    @OneToMany(mappedBy = "position", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val employeeProfiles: MutableList<EmployeeProfile> = mutableListOf()
)
