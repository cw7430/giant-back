package com.giant.auth

import com.giant.auth.dto.request.SignInRequestDto
import com.giant.auth.dto.response.RefreshRequestDto
import com.giant.auth.dto.response.SignInResponseDto
import com.giant.auth.repository.AccountRepository
import com.giant.auth.repository.AccountRoleRepository
import com.giant.common.api.code.ResponseCode
import com.giant.common.api.exception.CustomException
import com.giant.common.config.security.JwtProvider
import com.giant.common.config.security.JwtUtil
import jakarta.servlet.http.HttpServletRequest
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
            signInInfo.accountRoleName,
            signInInfo.employeeRoleName
        )
    }

    fun signOut(response: HttpServletResponse) {
        jwtUtil.removeRefreshTokenFromCookie(response)
    }

    fun refreshAccessToken(request: HttpServletRequest, response: HttpServletResponse, refreshRequestDto: RefreshRequestDto): SignInResponseDto {
        val accountId = jwtUtil.extractUserIdFromRefreshToken(request).toLong()
        val refreshInfo =
            accountRepository.findRefreshInfoByAccountId(accountId) ?: throw CustomException(ResponseCode.LOGIN_ERROR)

        val accessToken = jwtProvider.generateAccessToken(
            refreshInfo.accountId.toString(),
            refreshInfo.accountRoleId.toString()
        )

        val expiresAt = jwtUtil.extractExpirationFromAccessToken(accessToken)

        if(refreshRequestDto.isAuto) jwtUtil.addRefreshTokenToCookie(
            response,
            jwtProvider.generateRefreshToken(accountId.toString(), true),
            true
        )

        return SignInResponseDto(
            accessToken,
            expiresAt,
            refreshInfo.accountRoleName,
            refreshInfo.employeeRoleName
        )
    }
}