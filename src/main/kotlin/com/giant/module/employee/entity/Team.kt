package com.giant.module.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "team", schema = "employee", indexes = [Index(name = "ix_team_department", columnList = "department_id")])
class Team(
    @Id
    @Column(name = "id", nullable = false)
    var teamId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false, foreignKey = ForeignKey(name = "fk_team_department"))
    var department: Department,

    @Column(name = "team_name", nullable = false, unique = true)
    var teamName: String,
) {
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private var _employeeProfiles: MutableList<EmployeeProfile> = mutableListOf()

    val employeeProfiles: List<EmployeeProfile>
        get() = _employeeProfiles.toList()
}
