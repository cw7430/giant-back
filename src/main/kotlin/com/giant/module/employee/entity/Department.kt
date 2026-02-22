package com.giant.module.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "department", schema = "employee")
class Department(

    @Column(name = "department_code", nullable = false, unique = true)
    var departmentCode: String,

    @Column(name = "department_name", nullable = false, unique = true)
    var departmentName: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var departmentId: Long? = null
        protected set

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private val _teams: MutableList<Team> = mutableListOf()

    val teams: List<Team>
        get() = _teams.toList()

    companion object {
        fun create(
            departmentCode: String,
            departmentName: String
        ): Department {
            return Department(departmentCode, departmentName)
        }
    }
}
