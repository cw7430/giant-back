package com.giant.auth.repository.impl

import com.giant.auth.dto.SignInDto
import com.giant.auth.entity.QAccount
import com.giant.auth.repository.custom.AccountRepositoryCustom
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory

class AccountRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : AccountRepositoryCustom {

    override fun findSignInInfoByUserName(userName: String): SignInDto? {
        val account = QAccount.account
        return queryFactory
            .select(
                Projections.constructor(
                    SignInDto::class.java,
                    account.accountId,
                    account.passwordHash,
                    account.accountRole.accountRoleId
                )
            )
            .from(account)
            .where(account.userName.eq(userName))
            .fetchOne()
    }

}