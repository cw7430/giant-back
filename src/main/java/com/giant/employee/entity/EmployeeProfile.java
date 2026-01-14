package com.giant.employee.entity;

import com.giant.auth.entity.Account;
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
                @Index(name = "fk_profile_team", columnList = "team_id"),
                @Index(name = "fk_profile_role", columnList = "employee_role_id"),
                @Index(name = "fk_profile_position", columnList = "position_id"),
                @Index(name = "idx_profile_created_at", columnList = "created_at"),
                @Index(name = "idx_profile_updated_at", columnList = "updated_at"),
                @Index(name = "idx_profile_left_at", columnList = "left_at"),
                @Index(name = "idx_profile_created_by", columnList = "created_by"),
                @Index(name = "idx_profile_updated_by", columnList = "updated_by")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmployeeProfile {
    @Id
    @Column(name="id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_role_id", nullable = false, foreignKey = @ForeignKey(name = "fk_profile_role"))
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
            columnDefinition = "TIMESTAMP(3) WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')"
    )
    private Instant createdAt;

    @Column(
            name = "updated_at",
            nullable = false,
            columnDefinition = "TIMESTAMP(3) WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')"
    )
    private Instant updatedAt;

    @Column(name = "left_at", columnDefinition = "TIMESTAMP(3) WITHOUT TIME ZONE")
    private Instant leftAt = null;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
