package com.giant.employee.repository.impl

import com.giant.employee.dto.response.EmployeeProfileResponseDto
import com.giant.employee.entity.QEmployeeProfileView
import com.giant.employee.repository.custom.EmployeeProfileViewRepositoryCustom
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class EmployeeProfileViewRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : EmployeeProfileViewRepositoryCustom {
    override fun findAllByPageAndSize(
        page: Int,
        size: Int
    ): List<EmployeeProfileResponseDto> {
        val employee = QEmployeeProfileView.employeeProfileView

        return queryFactory
            .select(
                Projections.constructor(
                    EmployeeProfileResponseDto::class.java,
                    employee.employeeId,
                    employee.employeeCode,
                    employee.employeeName,
                    employee.positionName,
                    employee.employeeRoleName,
                    employee.departmentName,
                    employee.teamName,
                    employee.phoneNumber,
                    employee.email,
                    employee.createdEmployeeName,
                    employee.updatedEmployeeName,
                    employee.createdAt,
                    employee.updatedAt,
                    employee.leftAt
                )
            )
            .offset(page * size.toLong())
            .limit(size.toLong())
            .orderBy(employee.createdAt.asc())
            .orderBy(employee.positionId.asc())
            .orderBy(employee.employeeRoleId.asc())
            .orderBy(employee.departmentId.asc())
            .orderBy(employee.teamId.asc())
            .fetch()
    }
}