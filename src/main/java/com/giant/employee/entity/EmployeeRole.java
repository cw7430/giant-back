package com.giant.employee.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "employee_role", schema = "employee")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmployeeRole {
    @Id
    @Column(name = "id", nullable = false)
    private Long employeeRoleId;

    @Column(
            name = "role_name",
            nullable = false,
            unique = true
    )
    private String employeeRoleName;

    @OneToMany(mappedBy = "employeeRole", fetch = FetchType.LAZY)
    private List<EmployeeProfile> employeeProfiles;
}
