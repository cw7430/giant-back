package com.giant.employee.entity

import com.giant.auth.entity.Account
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Generated
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.generator.EventType
import java.time.Instant

@Entity
@Table(name = "employee_profile", schema = "employee")
data class EmployeeProfile(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val employeeId: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = ForeignKey(name = "fk_employee_account"))
    val account: Account,

    @Column(name = "employee_code", nullable = false, unique = true, length = 255)
    @Generated(event = [EventType.INSERT])
    val employeeCode: String? = null,

    @Column(name = "employee_name", nullable = false, length = 255)
    val employeeName: String,

    @Column(name = "phone_number", nullable = false, length = 15)
    val phoneNumber: String,

    @Column(name = "email", nullable = false, length = 255)
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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant
)