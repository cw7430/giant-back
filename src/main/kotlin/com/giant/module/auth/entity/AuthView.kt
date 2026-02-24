package com.giant.module.auth.entity

import com.giant.module.auth.entity.type.AuthRole
import com.giant.module.employee.entity.type.EmployeeRole
import jakarta.persistence.*
import org.hibernate.annotations.Immutable

@Entity
@Immutable
@Table(name = "auth_view", schema = "auth")
class AuthView(
    @Id
    @Column(name = "id")
    val accountId: Long,

    @Column(name = "user_name")
    val userName: String,

    @Column(name = "password_hash")
    val passwordHash: String,

    @Column(name = "auth_role")
    @Enumerated(EnumType.STRING)
    val authRole: AuthRole = AuthRole.USER,

    @Column(name = "phone_number")
    val phoneNumber: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "employee_code")
    val employeeCode: String,

    @Column(name = "employee_name")
    val employeeName: String,

    @Column(name = "employee_role")
    @Enumerated(EnumType.STRING)
    val employeeRole: EmployeeRole,

    @Column(name = "position_code")
    val positionCode: String,

    @Column(name = "position_name")
    val positionName: String,

    @Column(name = "department_code")
    val departmentCode: String,

    @Column(name = "department_name")
    val departmentName: String,

    @Column(name = "team_code")
    val teamCode: String,

    @Column(name = "team_name")
    val teamName: String,
)