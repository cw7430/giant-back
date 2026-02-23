package com.giant.module.employee.entity

import com.giant.module.employee.entity.type.EmployeeRole
import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "employee_profile_view", schema = "employee")
class EmployeeProfileView(
    @Id
    @Column(name = "id")
    val employeeId: Long,

    @Column(name = "employee_code")
    val employeeCode: String,

    @Column(name = "employee_role")
    @Enumerated(EnumType.STRING)
    val employeeRole: EmployeeRole,

    @Column(name = "employee_name")
    val employeeName: String,

    @Column(name = "position_code")
    val positionCode: String?,

    @Column(name = "position_name")
    val positionName: String?,

    @Column(name = "department_code")
    val departmentCode: String?,

    @Column(name = "department_name")
    val departmentName: String?,

    @Column(name = "team_code")
    val teamCode: String?,

    @Column(name = "team_name")
    val teamName: String?,

    @Column(name = "phone_number")
    val phoneNumber: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "created_employee_name")
    val createdEmployeeName: String?,

    @Column(name = "updated_employee_name")
    val updatedEmployeeName: String?,

    @Column(name = "created_at")
    val createdAt: Instant,

    @Column(name = "updated_at")
    val updatedAt: Instant,

    @Column(name = "left_at")
    val leftAt: Instant?
)