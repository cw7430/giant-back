package com.giant.module.employee.repository.impl

import com.giant.common.api.type.SortOrder
import com.giant.module.employee.dto.request.EmployeeProfilesRequestDto
import com.giant.module.employee.dto.response.EmployeeProfileResponseDto
import com.giant.module.employee.dto.type.EmployeeProfileSortPath
import com.giant.module.employee.entity.QEmployeeProfileView
import com.giant.module.employee.entity.type.EmployeeRole
import com.giant.module.employee.repository.custom.EmployeeProfileViewRepositoryCustom
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Wildcard
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class EmployeeProfileViewRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : EmployeeProfileViewRepositoryCustom {

    private fun getOrderSpecifiers(
        sortPath: EmployeeProfileSortPath,
        sortOrder: SortOrder,
        q: QEmployeeProfileView
    ): List<OrderSpecifier<*>> {
        val isDesc = sortOrder == SortOrder.DESC

        val paths = when (sortPath) {
            EmployeeProfileSortPath.POSITION -> listOf(q.positionCode, q.employeeCode, q.departmentCode, q.createdAt)
            EmployeeProfileSortPath.DEPARTMENT -> listOf(q.departmentCode, q.employeeCode, q.positionCode, q.createdAt)
            else -> listOf(q.employeeCode, q.positionCode, q.departmentCode, q.createdAt)
        }

        return paths.map { if (isDesc) it.desc() else it.asc() }
    }

    override fun findEmployeeProfiles(requestDto: EmployeeProfilesRequestDto): List<EmployeeProfileResponseDto> {
        val q = QEmployeeProfileView.employeeProfileView

        val orderSpecifiers = getOrderSpecifiers(
            requestDto.sortPath,
            requestDto.sortOrder,
            q
        )

        return queryFactory
            .select(
                Projections.constructor(
                    EmployeeProfileResponseDto::class.java,
                    q.employeeId,
                    q.employeeCode,
                    q.employeeRole,
                    q.employeeName,
                    q.positionCode,
                    q.positionName,
                    q.departmentCode,
                    q.departmentName,
                    q.teamCode,
                    q.teamName,
                    q.phoneNumber,
                    q.email,
                    q.createdBy,
                    q.createdEmployeeName,
                    q.updatedBy,
                    q.updatedEmployeeName,
                    q.createdAt,
                    q.updatedAt,
                    q.leftAt
                )
            )
            .from(q)
            .where(q.employeeRole.ne(EmployeeRole.LEFT))
            .orderBy(*orderSpecifiers.toTypedArray())
            .offset((requestDto.page - 1) * requestDto.size.toLong())
            .limit(requestDto.size.toLong())
            .fetch()
    }

    override fun countEmployeeProfiles(): Long {
        val q = QEmployeeProfileView.employeeProfileView

        return queryFactory
            .select(Wildcard.count)
            .from(q)
            .where(q.employeeRole.ne(EmployeeRole.LEFT))
            .fetchFirst() ?: 0L
    }

}