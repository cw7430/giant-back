package com.giant.module.auth.vo;

import com.giant.module.auth.entity.type.AuthRole;
import com.giant.module.employee.entity.type.EmployeeRole;

public record SignInVo(
        Long accountId,
        String passwordHash,
        AuthRole authRole,
        String employeeCode,
        String employeeName,
        EmployeeRole employeeRole,
        String departmentName,
        String teamName,
        String positionName
) {
}
