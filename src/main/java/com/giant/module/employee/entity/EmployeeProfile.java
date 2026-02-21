package com.giant.module.employee.entity;

import com.giant.module.auth.entity.Account;
import com.giant.module.employee.entity.type.EmployeeRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(
        name = "profile",
        schema = "employee",
        indexes = {
                @Index(name = "ix_profile_team", columnList = "team_id"),
                @Index(name = "ix_profile_position", columnList = "position_id"),
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmployeeProfile {
    @Id
    @Column(name = "id")
    private Long employeeId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_profile_account"))
    private Account account;

    @Column(
            name = "employee_code",
            nullable = false,
            unique = true
    )
    private String employeeCode;

    @Column(
            name = "employee_name",
            nullable = false
    )
    private String employeeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(name = "fk_profile_team"))
    private Team team;

    @Column(
            name = "employee_role",
            nullable = false,
            columnDefinition = "employee.employee_role"
    )
    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false, foreignKey = @ForeignKey(name = "fk_profile_position"))
    private Position position;

    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy = null;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP(6) WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')"
    )
    private Instant createdAt;

    @Column(
            name = "updated_at",
            nullable = false,
            columnDefinition = "TIMESTAMP(6) WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')"
    )
    private Instant updatedAt;

    @Column(
            name = "left_at",
            columnDefinition = "TIMESTAMP(6) WITH TIME ZONE"
    )
    private Instant leftAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

        /*constraint ck_employee_left_state
        check (((employee_role <> 'LEFT'::employee.employee_role) AND (left_at IS NULL)) OR
                ((employee_role = 'LEFT'::employee.employee_role) AND (left_at IS NOT NULL)))*/
        /*
        create index ix_active_employee_created_at
        on employee.profile (created_at desc)
        where (employee_role <> 'LEFT'::employee.employee_role);
        */
        /*
        create index ix_left_employee_left_at
        on employee.profile (left_at desc)
        where (employee_role = 'LEFT'::employee.employee_role);
        */
}
