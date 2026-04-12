package com.giant.module.employee

import com.giant.common.api.exception.CustomException
import com.giant.common.api.response.PageResponse
import com.giant.common.api.type.ResponseCode
import com.giant.common.config.security.JwtUtil
import com.giant.module.auth.entity.Account
import com.giant.module.auth.repository.AccountRepository
import com.giant.module.employee.dto.request.CreateEmployeeProfileRequestDto
import com.giant.module.employee.dto.request.EmployeeProfilesRequestDto
import com.giant.module.employee.dto.response.DepartmentResponseDto
import com.giant.module.employee.dto.response.EmployeeCodeResponseDto
import com.giant.module.employee.dto.response.EmployeeProfileResponseDto
import com.giant.module.employee.dto.response.PositionResponseDto
import com.giant.module.employee.entity.EmployeeProfile
import com.giant.module.employee.repository.*
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeService(
    private val accountRepository: AccountRepository,
    private val employeeProfileRepository: EmployeeProfileRepository,
    private val employeeSerialRepository: EmployeeSerialRepository,
    private val employeeProfileViewRepository: EmployeeProfileViewRepository,
    private val positionRepository: PositionRepository,
    private val departmentRepository: DepartmentRepository,
    private val teamRepository: TeamRepository,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder
) {
    private val log = KotlinLogging.logger {}

    fun formatEmployeeCode(serial: Long): String {
        val formattedSerial = serial.toString().padStart(3, '0')
        return "EMP$formattedSerial"
    }

    fun checkEmployeePermission(accountId: Long) {
        val requesterInfo = employeeProfileRepository.findByIdOrNull(accountId)
            ?: throw CustomException(ResponseCode.FORBIDDEN)
        val allowed = setOf("TM100", "TM200")
        if (requesterInfo.team.teamCode in allowed) {
            return
        }
        throw CustomException(ResponseCode.FORBIDDEN)
    }

    fun getEmployeeProfiles(requestDto: EmployeeProfilesRequestDto): PageResponse<EmployeeProfileResponseDto> {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val employeeProfiles = employeeProfileViewRepository.findEmployeeProfiles(requestDto)
        val totalElements = employeeProfileViewRepository.countEmployeeProfiles()
        log.info { "Employee Profiles requested by account ID: $accountId" }
        return PageResponse.of(
            content = employeeProfiles,
            pageRequest = requestDto,
            totalElements
        )
    }

    fun getEmployeeProfile(id: Long): EmployeeProfileResponseDto {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val employeeProfile = employeeProfileViewRepository.findByIdOrNull(id)
            ?: throw CustomException(ResponseCode.RESOURCE_NOT_FOUND)
        log.info { "Employee Profile requested by account ID: $accountId" }
        log.info { "employee ID: $id" }
        return EmployeeProfileResponseDto.from(employeeProfile)
    }

    fun getPositions(): List<PositionResponseDto> {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val positions = positionRepository.findAll()
        log.info { "Position requested by account ID: $accountId" }

        return positions.map { position ->
            PositionResponseDto.from(position)
        }
    }

    fun getDepartments(): List<DepartmentResponseDto> {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val departments = departmentRepository.findAll()
        log.info { "Department requested by account ID: $accountId" }

        return departments.map { dept ->
            DepartmentResponseDto.from(dept)
        }
    }

    fun getEmployeeCode(): EmployeeCodeResponseDto {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val employeeSerial = employeeSerialRepository.findBySerialName("EMPLOYEE_CODE_NO")
            ?: throw CustomException(ResponseCode.RESOURCE_NOT_FOUND)
        val employeeCode = formatEmployeeCode(employeeSerial.serialValue)
        log.info { "Employee Code requested by account ID: $accountId" }
        return EmployeeCodeResponseDto(employeeCode)
    }

    @Transactional
    fun createEmployee(requestDto: CreateEmployeeProfileRequestDto) {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        checkEmployeePermission(accountId)
        val employeeSerial = employeeSerialRepository.findBySerialName("EMPLOYEE_CODE_NO")
            ?: throw CustomException(ResponseCode.RESOURCE_NOT_FOUND)
        val employeeCode = formatEmployeeCode(employeeSerial.serialValue)
        val passwordHash = passwordEncoder.encode(employeeCode)!!

        val newTeam = teamRepository.findByTeamCode(requestDto.teamCode)
            ?: throw CustomException(ResponseCode.RESOURCE_NOT_FOUND)

        val newPosition = positionRepository.findByPositionCode(requestDto.positionCode)
            ?: throw CustomException(ResponseCode.RESOURCE_NOT_FOUND)

        val newAccount = accountRepository.save(
            Account.create(
                userName = employeeCode,
                passwordHash,
                phoneNumber = requestDto.phoneNumber,
                email = requestDto.email
            )
        )

        val newEmployeeProfile = employeeProfileRepository.save(
            EmployeeProfile.create(
                account = newAccount,
                employeeCode,
                employeeName = requestDto.employeeName,
                team = newTeam,
                employeeRole = requestDto.employeeRole,
                position = newPosition,
                createdBy = accountId,
                updatedBy = accountId
            )
        )

        employeeSerial.increaseSerialValue()

        log.info { "Create Employee successfully for account ID: $accountId" }
        log.info { "Created Employee: ${newEmployeeProfile.employeeId}" }
    }
}