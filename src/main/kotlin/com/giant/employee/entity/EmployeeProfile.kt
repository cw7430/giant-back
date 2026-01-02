package com.giant.employee.entity

import com.giant.auth.entity.Account
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.time.Clock
import java.time.LocalDateTime

@Entity
@Table(
    name = "employee_profile",
    schema = "employee",
    indexes = [
        Index(name = "idx_employee_team_id", columnList = "team_id"),
        Index(name = "idx_employee_role_id", columnList = "employee_role_id"),
        Index(name = "idx_employee_position_id", columnList = "position_id")
    ]
)
@DynamicUpdate
class EmployeeProfile(

    @Id
    @Column(name = "id")
    var id: Long = 0,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false, foreignKey = ForeignKey(name = "fk_employee_account"))
    var account: Account? = null,

    @Column(
        name = "employee_code",
        nullable = false,
        unique = true,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    var employeeCode: String = "",

    @Column(
        name = "employee_name",
        nullable = false,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    var employeeName: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, foreignKey = ForeignKey(name = "fk_employee_team"))
    var team: Team? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_role_id", nullable = false, foreignKey = ForeignKey(name = "fk_employee_role"))
    var employeeRole: EmployeeRole? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false, foreignKey = ForeignKey(name = "fk_employee_position"))
    var position: Position? = null,

    @Column(name = "created_by", nullable = false, updatable = false)
    var createdBy: Long = 0,

    @Column(name = "updated_by")
    var updatedBy: Long? = 0,

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false,
        columnDefinition = "datetime2(3) default SYSUTCDATETIME()"
    )
    var createdAt: LocalDateTime? = null,

    @Column(
        name = "updated_at",
        nullable = false,
        columnDefinition = "datetime2(3) default SYSUTCDATETIME()"
    )
    var updatedAt: LocalDateTime? = null,

    @Column(name = "left_at", columnDefinition = "datetime2(3)")
    var leftAt: LocalDateTime? = null
) {
    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now(Clock.systemUTC())
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now(Clock.systemUTC())
    }
}