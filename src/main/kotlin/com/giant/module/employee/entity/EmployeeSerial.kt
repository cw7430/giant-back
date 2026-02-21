package com.giant.module.employee.entity

import jakarta.persistence.*

@Entity
@Table(name = "\"serial\"", schema = "employee")
class EmployeeSerial(
    @Column(name = "serial_name", nullable = false, unique = true)
    var serialName: String,

    @Column(name = "serial_value", nullable = false)
    var serialValue: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val serialId: Long? = null
}