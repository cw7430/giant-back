package com.giant.auth.repository.impl

import com.giant.auth.dto.SignInDto
import com.giant.auth.entity.QAccount
import com.giant.auth.entity.QAccountRole
import com.giant.auth.repository.custom.AccountRepositoryCustom
import com.giant.employee.entity.QDepartment
import com.giant.employee.entity.QEmployeeProfile
import com.giant.employee.entity.QEmployeeRole
import com.giant.employee.entity.QTeam
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.Clock
import java.time.LocalDateTime

@Repository
class AccountRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : AccountRepositoryCustom {

    private fun findSignInInfo(
        predicate: BooleanExpression
    ): SignInDto? {
        val account = QAccount.account
        val accountRole = QAccountRole.accountRole
        val employee = QEmployeeProfile.employeeProfile
        val employeeRole = QEmployeeRole.employeeRole
        val department = QDepartment.department
        val team = QTeam.team

        return queryFactory
            .select(
                Projections.constructor(
                    SignInDto::class.java,
                    account.accountId,
                    account.passwordHash,
                    accountRole.accountRoleId,
                    accountRole.accountRoleName,
                    employee.employeeName,
                    employeeRole.employeeRoleName,
                    department.departmentName,
                    team.teamName
                )
            )
            .from(account)
            .join(account.accountRole, accountRole)
            .join(account.employeeProfile, employee)
            .join(employee.employeeRole, employeeRole)
            .join(employee.team, team)
            .join(team.department, department)
            .where(predicate)
            .fetchOne()
    }

    override fun findSignInInfoByUserName(userName: String): SignInDto? =
        findSignInInfo(QAccount.account.userName.eq(userName))

    override fun findRefreshInfoByAccountId(accountId: Long): SignInDto? =
        findSignInInfo(QAccount.account.accountId.eq(accountId))

    override fun existsByUserName(userName: String): Boolean? =
        queryFactory.selectOne()
            .from(QAccount.account)
            .where(QAccount.account.userName.eq(userName))
            .fetchFirst() != null

    override fun findPasswordHashByAccountId(accountId: Long): String? =
        queryFactory.select(QAccount.account.passwordHash)
            .from(QAccount.account)
            .where(QAccount.account.accountId.eq(accountId))
            .fetchOne()

    override fun updateAccount(
        accountId: Long,
        userName: String,
        phoneNumber: String,
        email: String
    ): Long =
        queryFactory
            .update(QAccount.account)
            .set(QAccount.account.userName, userName)
            .set(QAccount.account.phoneNumber, phoneNumber)
            .set(QAccount.account.email, email)
            .set(QAccount.account.updatedAt, LocalDateTime.now(Clock.systemUTC()))
            .where(QAccount.account.accountId.eq(accountId))
            .execute()

    override fun updatePassword(accountId: Long, passwordHash: String): Long =
        queryFactory
            .update(QAccount.account)
            .set(QAccount.account.passwordHash, passwordHash)
            .set(QAccount.account.updatedAt, LocalDateTime.now(Clock.systemUTC()))
            .where(QAccount.account.accountId.eq(accountId))
            .execute()

}