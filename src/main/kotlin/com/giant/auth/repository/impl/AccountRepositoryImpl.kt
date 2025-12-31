package com.giant.auth.repository.impl

import com.giant.auth.dto.SignInDto
import com.giant.auth.entity.QAccount
import com.giant.auth.entity.QAccountRole
import com.giant.auth.repository.custom.AccountRepositoryCustom
import com.giant.employee.entity.QEmployeeProfile
import com.giant.employee.entity.QEmployeeRole
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory

class AccountRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : AccountRepositoryCustom {

    override fun findSignInInfoByUserName(userName: String): SignInDto? {
        val account = QAccount.account
        val accountRole = QAccountRole.accountRole
        val employee = QEmployeeProfile.employeeProfile
        val employeeRole = QEmployeeRole.employeeRole
        return queryFactory
            .select(
                Projections.constructor(
                    SignInDto::class.java,
                    account.accountId,
                    account.passwordHash,
                    accountRole.accountRoleId,
                    accountRole.accountRoleName,
                    employeeRole.employeeRoleId,
                    employeeRole.employeeRoleName
                )
            )
            .from(account)
            .join(account.accountRole, accountRole)
            .join(account.employeeProfile, employee)
            .join(employee.employeeRole, employeeRole)
            .where(account.userName.eq(userName))
            .fetchOne()
    }

}