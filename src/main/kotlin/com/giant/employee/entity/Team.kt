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
class Team(
    @Id
    @Column(name = "id", nullable = false)
    var teamId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false, foreignKey = ForeignKey(name = "fk_team_department"))
    var department: Department? = null,

    @Column(
        name = "team_name",
        nullable = false,
        unique = true,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    var teamName: String = "",

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    val employees: MutableList<EmployeeProfile> = mutableListOf()
)
