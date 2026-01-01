package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "position", schema = "employee")
data class Position(
    @Id
    @Column(name = "id", nullable = false)
    val positionId: Long,

    @Column(
        name = "position_name",
        nullable = false,
        unique = true,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    val positionName: String,

    @Column(name= "basic_salary", nullable = false)
    val basicSalary: Long,

    @Column(name= "incentive_salary", nullable = false)
    val incentiveSalary: Long,

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    val employeeProfiles: MutableList<EmployeeProfile> = mutableListOf()
)
