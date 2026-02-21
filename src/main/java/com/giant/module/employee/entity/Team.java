package com.giant.module.employee.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(
        name = "team",
        schema = "employee",
        indexes = {
                @Index(name = "ix_team_department", columnList = "department_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Team {
    @Id
    @Column(name = "id", nullable = false)
    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "department_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_team_department")
    )
    private Department department;

    @Column(
            name = "team_name",
            nullable = false,
            unique = true
    )
    private String teamName;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<EmployeeProfile> employeeProfiles;
}
