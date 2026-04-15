package com.giant.module.employee.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class SequenceGenerateRepository(
    @PersistenceContext private val em: EntityManager
) {
    fun nextEmployeeCode(): Long =
        (em.createNativeQuery("SELECT nextval('employee.employee_code_seq')")
            .singleResult as Number).toLong()
}