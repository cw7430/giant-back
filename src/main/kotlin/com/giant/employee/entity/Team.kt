package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "team",
    schema = "employee",
    indexes = [
        Index(name = "idx_team_department_id", columnList = "department_id")
    ]
)
data class Team(
    @Id
    @Column(name = "id", nullable = false)
    val teamId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false, foreignKey = ForeignKey(name = "fk_team_department"))
    val department: Department,

    @Column(name = "team_name", nullable = false, unique = true, length = 255)
    val teamName: String,

    @Column(name = "team_head_id")
    val teamHeadId: Long? = null,

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    val employees: MutableList<EmployeeProfile> = mutableListOf()
)
