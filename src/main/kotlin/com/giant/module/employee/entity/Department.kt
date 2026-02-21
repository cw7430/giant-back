package com.giant.module.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "department", schema = "employee")
class Department(
    @Id
    @Column(name = "id", nullable = false)
    var departmentId: Long,

    @Column(name = "department_name", nullable = false, unique = true)
    var departmentName: String,
) {
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private val _teams: MutableList<Team> = mutableListOf()

    val teams: List<Team>
        get() = _teams.toList()
}
