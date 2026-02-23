package com.giant.module.employee.entity

import com.giant.module.auth.entity.Account
import com.giant.module.employee.entity.type.EmployeeRole
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "profile",
    schema = "employee",
    indexes = [
        Index(name = "ix_profile_team", columnList = "team_id"),
        Index(name = "ix_profile_position", columnList = "position_id"),
        Index(name = "ix_profile_created_by", columnList = "created_by"),
        Index(name = "ix_profile_updated_by", columnList = "updated_by"),
    ]
)
class EmployeeProfile(
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false, foreignKey = ForeignKey(name = "fk_profile_account"))
    var account: Account,

    @Column(name = "employee_code", nullable = false, unique = true)
    var employeeCode: String,

    @Column(name = "employee_name", nullable = false)
    var employeeName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, foreignKey = ForeignKey(name = "fk_profile_team"))
    var team: Team,

    @Column(name = "employee_role", nullable = false)
    @Enumerated(EnumType.STRING)
    var employeeRole: EmployeeRole,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false, foreignKey = ForeignKey(name = "fk_profile_position"))
    var position: Position,

    @Column(name = "created_by", nullable = false, updatable = false)
    var createdBy: Long,

    @Column(name = "updated_by", nullable = false)
    var updatedBy: Long,

    @Column(name = "left_at")
    var leftAt: Instant? = null
) {
    @Id
    @Column(name = "id")
    var employeeId: Long? = null
        protected set

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    var createdAt: Instant? = Instant.now()

    @Column(
        name = "updated_at",
        nullable = false
    )
    var updatedAt: Instant? = Instant.now()

    @PrePersist
    fun onCreate() {
        val now = Instant.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = Instant.now()
    }

    companion object {
        fun create(
            account: Account,
            employeeCode: String,
            employeeName: String,
            team: Team,
            employeeRole: EmployeeRole,
            position: Position,
            createdBy: Long,
            updatedBy: Long
        ): EmployeeProfile {
            return EmployeeProfile(
                account,
                employeeCode,
                employeeName,
                team,
                employeeRole,
                position,
                createdBy,
                updatedBy
            )
        }
    }
}

/*constraint ck_employee_left_state
       check (((employee_role <> 'LEFT'::employee.employee_role) AND (left_at IS NULL)) OR
               ((employee_role = 'LEFT'::employee.employee_role) AND (left_at IS NOT NULL)))*/
/*
    create index ix_active_employee_created_at
    on employee.profile (created_at desc)
    where (employee_role <> 'LEFT'::employee.employee_role);
    */
/*
    create index ix_left_employee_left_at
    on employee.profile (left_at desc)
    where (employee_role = 'LEFT'::employee.employee_role);
    */