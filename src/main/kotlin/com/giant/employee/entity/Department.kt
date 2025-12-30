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
        columnDefinition = "nvarchar(255)",
        length = 255
    )
    val departmentName: String,

    @Column(name = "department_head_id", unique = true)
    val departmentHeadId: Long? = null,

    @OneToMany(mappedBy = "department", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val teams: MutableList<Team> = mutableListOf()
)