package com.giant.auth.repository.impl;

import com.giant.auth.dto.SignInDto;
import com.giant.auth.entity.QAccount;
import com.giant.auth.entity.QAccountRole;
import com.giant.auth.repository.custom.AccountRepositoryCustom;
import com.giant.employee.entity.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private SignInDto findSignInInfo(BooleanExpression predicate) {
        QAccount account = QAccount.account;
        QAccountRole accountRole = QAccountRole.accountRole;
        QDepartment department = QDepartment.department;
        QTeam team = QTeam.team;
        QPosition position = QPosition.position;
        QEmployeeRole employeeRole = QEmployeeRole.employeeRole;
        QEmployeeProfile employeeProfile = QEmployeeProfile.employeeProfile;

        return queryFactory
                .select(
                        Projections.constructor(
                                SignInDto.class,
                                account.accountId,
                                account.passwordHash,
                                accountRole.accountRoleId,
                                accountRole.accountRoleName,
                                employeeProfile.employeeCode,
                                employeeProfile.employeeName,
                                employeeRole.employeeRoleName,
                                department.departmentName,
                                team.teamName,
                                position.positionName
                        )
                )
                .from(account)
                .join(account.accountRole, accountRole)
                .join(account.employeeProfile, employeeProfile)
                .join(employeeProfile.team, team)
                .join(employeeProfile.position, position)
                .join(employeeProfile.employeeRole, employeeRole)
                .where(predicate)
                .fetchOne();
    }

    @Override
    public SignInDto findSignInInfoByUserName(String userName) {
       return findSignInInfo(QAccount.account.userName.eq(userName));
    }

    @Override
    public SignInDto findRefreshInfoByAccountId(Long accountId) {
        return findSignInInfo(QAccount.account.accountId.eq(accountId));
    }
}
