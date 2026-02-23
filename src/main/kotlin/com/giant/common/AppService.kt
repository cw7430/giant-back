package com.giant.common

import com.giant.module.auth.repository.AccountRepository
import com.giant.module.auth.repository.RefreshTokenRepository
import com.giant.module.employee.repository.DepartmentRepository
import com.giant.module.employee.repository.EmployeeProfileRepository
import com.giant.module.employee.repository.PositionRepository
import com.giant.module.employee.repository.TeamRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class AppService(
    private val accountRepository: AccountRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val employeeProfileRepository: EmployeeProfileRepository,
    private val positionRepository: PositionRepository,
    private val departmentRepository: DepartmentRepository,
    private val teamRepository: TeamRepository,
    private val employeeSerialRepository: EmployeeProfileRepository
) {
    private val log = KotlinLogging.logger {}

    fun queryTest() {
        log.info { "account table" }
        accountRepository.findAll()
        log.info { "refresh_token table" }
        refreshTokenRepository.findAll()
        log.info { "profile table" }
        employeeProfileRepository.findAll()
        log.info { "position table" }
        positionRepository.findAll()
        log.info { "department table" }
        departmentRepository.findAll()
        log.info { "team table" }
        teamRepository.findAll()
        log.info { "serial table" }
        employeeSerialRepository.findAll()
    }
}