package com.giant.auth

import com.giant.auth.dto.request.SignInRequestDto
import com.giant.auth.dto.response.SignInResponseDto
import com.giant.auth.repository.AccountRepository
import com.giant.auth.repository.AccountRoleRepository
import com.giant.common.api.code.ResponseCode
import com.giant.common.api.exception.CustomException
import com.giant.common.config.security.JwtProvider
import com.giant.common.config.security.JwtUtil
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val accountRepository: AccountRepository,
    private val accountRoleRepository: AccountRoleRepository,
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
            )  throw CustomException(ResponseCode.LOGIN_ERROR)

        val accessToken = jwtProvider.generateAccessToken(
            signInInfo.accountId.toString(),
            signInInfo.accountRoleId.toString(),
            signInInfo.employeeRoleId.toString()
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
            signInInfo.accountRoleName,
            signInInfo.employeeRoleName
        )
    }
}