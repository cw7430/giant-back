package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "\"serial\"", schema = "employee")
data class EmployeeSerial(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val serialId: Long = 0,

    @Column(
        name = "serial_name",
        nullable = false,
        unique = true,
        length = 255,
        columnDefinition = "nvarchar(255) COLLATE Latin1_General_100_CI_AS_SC"
    )
    val serialName: String,

    @Column(name = "serial_value", nullable = false)
    val serialValue: Long
)