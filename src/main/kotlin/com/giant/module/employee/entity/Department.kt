package com.giant.module.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "department", schema = "employee")
class Department(
    @Id
    @Column(name = "id", nullable = false)
    val departmentId: Long? = null,

    @Column(name = "department_name", nullable = false, unique = true)
    var departmentName: String? = null,

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    var teams: MutableList<Team> = mutableListOf()
) {
}
