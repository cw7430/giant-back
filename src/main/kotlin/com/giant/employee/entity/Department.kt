package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "department", schema = "employee")
data class Department(
    @Id
    @Column(name = "id", nullable = false)
    val departmentId: Long,

    @Column(
        name = "department_name",
        nullable = false,
        unique = true,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC",
        length = 255
    )
    val departmentName: String,

    @OneToMany(mappedBy = "department", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val teams: MutableList<Team> = mutableListOf()
)