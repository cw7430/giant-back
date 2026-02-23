package com.giant.module.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "team", schema = "employee", indexes = [Index(name = "ix_team_department", columnList = "department_id")])
class Team(
    @Column(name = "team_code", nullable = false, unique = true)
    var teamCode: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false, foreignKey = ForeignKey(name = "fk_team_department"))
    var department: Department,

    @Column(name = "team_name", nullable = false, unique = true)
    var teamName: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val teamId: Long? = null

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private var _employeeProfiles: MutableList<EmployeeProfile> = mutableListOf()

    val employeeProfiles: List<EmployeeProfile>
        get() = _employeeProfiles.toList()

    companion object {
        fun create(
            teamCode: String,
            department: Department,
            teamName: String
        ): Team {
            return Team(teamCode, department, teamName)
        }
    }
}
