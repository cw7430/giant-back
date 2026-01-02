package com.giant.employee.entity

import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import java.time.LocalDateTime

@Entity
@Immutable
@Table(
    name = "employee_profile_view",
    schema = "employee"
)
class EmployeeProfileView (
    @Id
    @Column(name = "id")
    val employeeId: Long,

    @Column(name = "employee_code")
    val employeeCode: String?,

    @Column(name = "employee_name")
    val employeeName: String?,

    @Column(name = "position_name")
    val positionName: String?,

    @Column(name = "employee_role_name")
    val employeeRoleName: String?,

    @Column(name = "department_name")
    val departmentName: String?,

    @Column(name = "team_name")
    val teamName: String?,

    @Column(name = "phone_number")
    val phoneNumber: String?,

    @Column(name = "email")
    val email: String?,

    @Column(name = "created_employee_name")
    val createdEmployeeName: String?,

    @Column(name = "updated_employee_name")
    val updatedEmployeeName: String?,

    @Column(name = "created_at")
    val createdAt: LocalDateTime?,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime?,

    @Column(name = "left_at")
    val leftAt: LocalDateTime?
)