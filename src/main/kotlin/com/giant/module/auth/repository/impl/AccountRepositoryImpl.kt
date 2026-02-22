package com.giant.module.auth.repository.impl

import com.giant.module.auth.dto.vo.SignInVo
import com.giant.module.auth.entity.QAccount
import com.giant.module.auth.repository.custom.AccountRepositoryCustom
import com.giant.module.employee.entity.*
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository


@Repository
class AccountRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : AccountRepositoryCustom {

    private fun findSignInInfo(predicate: BooleanExpression?): SignInVo? {
        val account = QAccount.account
        val department = QDepartment.department
        val team = QTeam.team
        val position = QPosition.position
        val employeeProfile = QEmployeeProfile.employeeProfile

        return queryFactory
            .select(
                Projections.constructor(
                    SignInVo::class.java,
                    account.accountId,
                    account.passwordHash,
                    account.authRole,
                    employeeProfile.employeeCode,
                    employeeProfile.employeeName,
                    employeeProfile.employeeRole,
                    department.departmentCode,
                    team.teamCode,
                    position.positionCode
                )
            )
            .from(account)
            .join(account.employeeProfile, employeeProfile)
            .join(employeeProfile.team, team)
            .join(employeeProfile.position, position)
            .where(predicate)
            .fetchOne()
    }

    override fun findSignInInfoByUserName(userName: String): SignInVo? {
        return findSignInInfo(QAccount.account.userName.eq(userName))
    }

    override fun findRefreshInfoByAccountId(accountId: Long): SignInVo? {
        return findSignInInfo(QAccount.account.accountId.eq(accountId))
    }
}