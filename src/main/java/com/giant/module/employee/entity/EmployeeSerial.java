package com.giant.module.employee.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"serial\"", schema = "employee")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmployeeSerial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long serialId;

    @Column(
            name = "serial_name",
            nullable = false,
            unique = true
    )
    private String serialName;

    @Column(name = "serial_value", nullable = false)
    private Long serialValue;
}
