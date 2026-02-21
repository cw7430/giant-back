package com.giant.module.auth.repository.impl;

import com.giant.module.auth.entity.QAccount;
import com.giant.module.auth.repository.custom.AccountRepositoryCustom;
import com.giant.module.auth.vo.SignInVo;
import com.giant.module.employee.entity.QDepartment;
import com.giant.module.employee.entity.QEmployeeProfile;
import com.giant.module.employee.entity.QPosition;
import com.giant.module.employee.entity.QTeam;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private SignInVo findSignInInfo(BooleanExpression predicate) {
        QAccount account = QAccount.account;
        QDepartment department = QDepartment.department;
        QTeam team = QTeam.team;
        QPosition position = QPosition.position;
        QEmployeeProfile employeeProfile = QEmployeeProfile.employeeProfile;

        return queryFactory
                .select(
                        Projections.constructor(
                                SignInVo.class,
                                account.accountId,
                                account.passwordHash,
                                account.authRole,
                                employeeProfile.employeeCode,
                                employeeProfile.employeeName,
                                employeeProfile.employeeRole,
                                department.departmentName,
                                team.teamName,
                                position.positionName
                        )
                )
                .from(account)
                .join(account.employeeProfile, employeeProfile)
                .join(employeeProfile.team, team)
                .join(employeeProfile.position, position)
                .where(predicate)
                .fetchOne();
    }

    @Override
    public Optional<SignInVo> findSignInInfoByUserName(String userName) {
        return Optional.ofNullable(findSignInInfo(QAccount.account.userName.eq(userName)));
    }

    @Override
    public Optional<SignInVo> findRefreshInfoByAccountId(Long accountId) {
        return Optional.ofNullable(findSignInInfo(QAccount.account.accountId.eq(accountId)));
    }
}
