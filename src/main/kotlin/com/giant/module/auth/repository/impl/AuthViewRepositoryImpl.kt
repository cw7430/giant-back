package com.giant.module.auth.repository.impl

import com.giant.module.auth.dto.vo.SignInVo
import com.giant.module.auth.entity.QAuthView
import com.giant.module.auth.repository.custom.AuthViewRepositoryCustom
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class AuthViewRepositoryImpl(private val queryFactory: JPAQueryFactory) : AuthViewRepositoryCustom {

    private fun findSignInInfo(predicate: BooleanExpression?): SignInVo? {
        val authView = QAuthView.authView

        return queryFactory
            .select(
                Projections.constructor(
                    SignInVo::class.java,
                    authView.accountId,
                    authView.passwordHash,
                    authView.authRole,
                    authView.employeeCode,
                    authView.employeeName,
                    authView.employeeRole,
                    authView.departmentCode,
                    authView.teamCode,
                    authView.positionCode
                )
            )
            .from(authView)
            .where(predicate)
            .fetchOne()
    }

    override fun findSignInInfoByUserName(userName: String): SignInVo? {
        return findSignInInfo(QAuthView.authView.userName.eq(userName))
    }

    override fun findRefreshInfoByAccountId(accountId: Long): SignInVo? {
        return findSignInInfo(QAuthView.authView.accountId.eq(accountId))
    }
}