package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "department", schema = "employee")
class Department(
    @Id
    @Column(name = "id", nullable = false)
    var departmentId: Long = 0,

    @Column(
        name = "department_name",
        nullable = false,
        unique = true,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC",
        length = 255
    )
    var departmentName: String = "",

    @OneToMany(mappedBy = "department", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val teams: MutableList<Team> = mutableListOf()
)