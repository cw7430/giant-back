package com.giant.module.employee.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "position", schema = "employee")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Position {
    @Id
    @Column(name = "id", nullable = false)
    private Long positionId;

    @Column(
            name = "position_name",
            nullable = false,
            unique = true
    )
    private String positionName;

    @Column(name = "basic_salary", nullable = false)
    private long basicSalary;

    @Column(name = "incentive_salary", nullable = false)
    private long incentiveSalary;

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private List<EmployeeProfile> employeeProfiles;
}
