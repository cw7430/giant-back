package com.giant.module.employee.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "department", schema = "employee")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Department {
    @Id
    @Column(name = "id", nullable = false)
    private Long departmentId;

    @Column(
            name = "department_name",
            nullable = false,
            unique = true
    )
    private String departmentName;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Team> teams;
}
