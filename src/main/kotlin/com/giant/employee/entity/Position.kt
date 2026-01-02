package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "position", schema = "employee")
class Position(
    @Id
    @Column(name = "id", nullable = false)
    var positionId: Long = 0,

    @Column(
        name = "position_name",
        nullable = false,
        unique = true,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    var positionName: String = "",

    @Column(name= "basic_salary", nullable = false)
    var basicSalary: Long = 0,

    @Column(name= "incentive_salary", nullable = false)
    var incentiveSalary: Long = 0,

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    val employeeProfiles: MutableList<EmployeeProfile> = mutableListOf()
)
