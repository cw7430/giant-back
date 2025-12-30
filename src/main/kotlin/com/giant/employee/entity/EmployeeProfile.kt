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
        Index(name = "idx_employee_account_id", columnList = "account_id"),
        Index(name = "idx_employee_team_id", columnList = "team_id"),
        Index(name = "idx_employee_role_id", columnList = "employee_role_id"),
        Index(name = "idx_employee_position_id", columnList = "position_id")
    ]
)
@DynamicUpdate
data class EmployeeProfile(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val employeeId: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = ForeignKey(name = "fk_employee_account"))
    val account: Account,

    @Column(
        name = "employee_code",
        nullable = false,
        unique = true,
        length = 255,
        columnDefinition = "nvarchar(255)"
    )
    val employeeCode: String,

    @Column(
        name = "employee_name",
        nullable = false,
        length = 255,
        columnDefinition = "nvarchar(255)"
    )
    val employeeName: String,

    @Column(
        name = "phone_number",
        nullable = false,
        length = 15,
        columnDefinition = "nvarchar(15)"
    )
    val phoneNumber: String,

    @Column(name = "email", nullable = false, length = 255, columnDefinition = "nvarchar(255)")
    val email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, foreignKey = ForeignKey(name = "fk_employee_team"))
    val team: Team,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_role_id", nullable = false, foreignKey = ForeignKey(name = "fk_employee_role"))
    val employeeRole: EmployeeRole,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false, foreignKey = ForeignKey(name = "fk_employee_position"))
    val position: Position,

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