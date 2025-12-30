package com.giant.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "\"serial\"", schema = "employee")
data class EmployeeSerial(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val serialId: Long,

    @Column(name = "serial_name", nullable = false, unique = true, length = 255)
    val serialName: String,

    @Column(name = "serial_value")
    val serialValue: Long
)