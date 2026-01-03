package com.giant.auth

import com.giant.auth.dto.request.*
import com.giant.auth.dto.response.SignInResponseDto
import com.giant.auth.repository.AccountRepository
import com.giant.common.api.code.ResponseCode
import com.giant.common.api.exception.CustomException
import com.giant.common.config.security.JwtProvider
import com.giant.common.config.security.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val jwtUtil: JwtUtil
) {

    fun signIn(response: HttpServletResponse, signInRequestDto: SignInRequestDto): SignInResponseDto {
        val signInInfo = accountRepository.findSignInInfoByUserName(signInRequestDto.userName)

        if (
            signInInfo == null || !passwordEncoder.matches(
                signInRequestDto.password, signInInfo.passwordHash
            )
        ) throw CustomException(ResponseCode.LOGIN_ERROR)

        if (signInInfo.accountRoleId == 3L) {
            throw CustomException(ResponseCode.FORBIDDEN)
        }

        val accessToken = jwtProvider.generateAccessToken(
            signInInfo.accountId.toString(),
            signInInfo.accountRoleId.toString()
        )

        val expiresAt = jwtUtil.extractExpirationFromAccessToken(accessToken)

        jwtUtil.addRefreshTokenToCookie(
            response,
            jwtProvider.generateRefreshToken(signInInfo.accountId.toString(), signInRequestDto.isAuto),
            signInRequestDto.isAuto
        )

        return SignInResponseDto(
            accessToken,
            expiresAt,
            signInInfo.employeeName,
            signInInfo.accountRoleName,
            signInInfo.employeeRoleName
        )
    }

    fun signOut(response: HttpServletResponse) {
        jwtUtil.removeRefreshTokenFromCookie(response)
    }

    fun refreshAccessToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
        refreshRequestDto: RefreshRequestDto
    ): SignInResponseDto {
        val accountId = jwtUtil.extractUserIdFromRefreshToken(request).toLong()
        val refreshInfo =
            accountRepository.findRefreshInfoByAccountId(accountId) ?: throw CustomException(ResponseCode.UNAUTHORIZED)

        if (refreshInfo.accountRoleId == 3L) {
            throw CustomException(ResponseCode.FORBIDDEN)
        }

        val accessToken = jwtProvider.generateAccessToken(
            refreshInfo.accountId.toString(),
            refreshInfo.accountRoleId.toString()
        )

        val expiresAt = jwtUtil.extractExpirationFromAccessToken(accessToken)

        if (refreshRequestDto.isAuto) jwtUtil.addRefreshTokenToCookie(
            response,
            jwtProvider.generateRefreshToken(accountId.toString(), true),
            true
        )

        return SignInResponseDto(
            accessToken,
            expiresAt,
            refreshInfo.employeeName,
            refreshInfo.accountRoleName,
            refreshInfo.employeeRoleName
        )
    }

    fun checkUserNameDuplicate(
        checkUserNameDuplicateRequestDto: CheckUserNameDuplicateRequestDto
    ) {
        if (accountRepository.existsByUserName(checkUserNameDuplicateRequestDto.userName))
            throw (CustomException(ResponseCode.DUPLICATE_RESOURCE))
    }

    @Transactional
    fun updateAccountInfo(
        request: HttpServletRequest,
        updateAccountInfoRequestDto: UpdateAccountInfoRequestDto
    ) {
        val accountId = jwtUtil.extractUserIdFromAccessToken(request).toLong()

        if (accountRepository.existsByUserName(updateAccountInfoRequestDto.userName))
            throw (CustomException(ResponseCode.DUPLICATE_RESOURCE))

        val account = accountRepository.findById(accountId)
            .orElseThrow { CustomException(ResponseCode.RESOURCE_NOT_FOUND) }

        if (!passwordEncoder.matches(
                updateAccountInfoRequestDto.password,
                account.passwordHash
            )
        ) throw CustomException(ResponseCode.UNAUTHORIZED)

        account.updateAccountInfo(
            updateAccountInfoRequestDto.userName,
            updateAccountInfoRequestDto.phoneNumber,
            updateAccountInfoRequestDto.email
        )
        accountRepository.save(account)
    }

    @Transactional
    fun updatePassword(
        request: HttpServletRequest,
        updatePasswordRequestDto: UpdatePasswordRequestDto
    ) {
        val accountId = jwtUtil.extractUserIdFromAccessToken(request).toLong()

        val account = accountRepository.findById(accountId)
            .orElseThrow { CustomException(ResponseCode.RESOURCE_NOT_FOUND) }

        if (!passwordEncoder.matches(
                updatePasswordRequestDto.prevPassword,
                account.passwordHash
            )
        ) throw CustomException(ResponseCode.UNAUTHORIZED)

        val newPasswordHash = passwordEncoder.encode(updatePasswordRequestDto.newPassword)

        account.updatePassword(newPasswordHash)
        accountRepository.save(account)
    }
}