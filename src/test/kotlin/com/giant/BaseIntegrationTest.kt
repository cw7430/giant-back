package com.giant

import com.giant.module.auth.entity.Account
import com.giant.module.auth.repository.AccountRepository
import com.giant.module.employee.entity.Department
import com.giant.module.employee.entity.EmployeeProfile
import com.giant.module.employee.entity.EmployeeSerial
import com.giant.module.employee.entity.Position
import com.giant.module.employee.entity.Team
import com.giant.module.employee.entity.type.EmployeeRole
import com.giant.module.employee.repository.DepartmentRepository
import com.giant.module.employee.repository.EmployeeProfileRepository
import com.giant.module.employee.repository.EmployeeSerialRepository
import com.giant.module.employee.repository.PositionRepository
import com.giant.module.employee.repository.TeamRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
abstract class BaseIntegrationTest {
    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var positionRepository: PositionRepository

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    @Autowired
    lateinit var teamRepository: TeamRepository

    @Autowired
    lateinit var employeeSerialRepository: EmployeeSerialRepository

    @Autowired
    lateinit var employeeProfileRepository: EmployeeProfileRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        val testPasswordHash = passwordEncoder.encode("0000")!!
        val testEmployeeSerial = employeeSerialRepository.save(
            EmployeeSerial.create("EMPLOYEE_CODE_NO", 1L)
        )
        val testEmployeeCode =
            "EMP" + testEmployeeSerial.serialValue
                .toString()
                .padStart(3, '0')
        val testAccount = accountRepository.save(
            Account.create(
                userName = testEmployeeCode,
                passwordHash = testPasswordHash,
                phoneNumber = "010-1234-1234",
                email = "email@email.com"
            )
        )
        val testAccountId = testAccount.accountId!!
        val testPosition = positionRepository.save(
            Position.create(
                positionCode = "PSN10",
                positionName = "대표이사",
                basicSalary = 100000000L,
                incentiveSalary = 25000000L
            )
        )
        val testDepartment = departmentRepository.save(
            Department.create(
                departmentCode = "DPT100",
                departmentName = "경영지원부"
            )
        )
        val testTeam = teamRepository.save(
            Team.create(
                teamCode = "TM100",
                department = testDepartment,
                teamName = "경영팀"
            )
        )
        employeeProfileRepository.save(
            EmployeeProfile.create(
                account = testAccount,
                employeeCode = testEmployeeCode,
                employeeName = "이사장",
                team = testTeam,
                employeeRole = EmployeeRole.DEPARTMENT_CHIEF,
                position = testPosition,
                createdBy = testAccountId,
                updatedBy = testAccountId,
            )
        )
    }

    @AfterEach
    fun tearDown() {
        employeeProfileRepository.deleteAll()
        teamRepository.deleteAll()
        departmentRepository.deleteAll()
        positionRepository.deleteAll()
        accountRepository.deleteAll()
        employeeSerialRepository.deleteAll()
    }
}