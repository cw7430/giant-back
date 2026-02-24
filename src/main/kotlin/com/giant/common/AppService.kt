package com.giant.common

import com.giant.module.auth.repository.AccountRepository
import com.giant.module.auth.repository.RefreshTokenRepository
import com.giant.module.employee.repository.*
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AppService(
    private val accountRepository: AccountRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val employeeProfileRepository: EmployeeProfileRepository,
    private val positionRepository: PositionRepository,
    private val departmentRepository: DepartmentRepository,
    private val teamRepository: TeamRepository,
    private val employeeSerialRepository: EmployeeSerialRepository
) {
    private val log = KotlinLogging.logger {}

    fun queryTest() {
        val account = accountRepository.findByIdOrNull(1)
        log.info { "account table called: ${account.toString()}" }
        val refreshToken = refreshTokenRepository.findByIdOrNull(1)
        log.info { "refresh_token table called: ${refreshToken.toString()}" }
        val profile = employeeProfileRepository.findByIdOrNull(1)
        log.info { "profile table called - 1 : ${profile.toString()}" }
        if(account != null) {
            val profile2 = employeeProfileRepository.findByAccount(account)
            log.info { "profile table called - 2 : ${profile2.toString()}" }
        }
        val position = positionRepository.findByIdOrNull(1)
        log.info { "position table called: ${position.toString()}" }
        val department = departmentRepository.findByIdOrNull(1)
        log.info { "department table called: ${department.toString()}" }
        val team = teamRepository.findByIdOrNull(1)
        log.info { "team table called: ${team.toString()}" }
        val employeeSerial = employeeSerialRepository.findByIdOrNull(1)
        log.info { "serial table called: ${employeeSerial.toString()}" }
    }
}