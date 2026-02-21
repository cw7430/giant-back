package com.giant.module.auth

import com.giant.common.api.exception.CustomException
import com.giant.common.api.type.ResponseCode
import com.giant.common.config.security.JwtProvider
import com.giant.common.config.security.JwtUtil
import com.giant.module.auth.dto.request.SignInRequestDto
import com.giant.module.auth.dto.response.SignInResponseDto
import com.giant.module.auth.vo.SignInVo
import com.giant.module.auth.entity.Account
import com.giant.module.auth.entity.RefreshToken
import com.giant.module.auth.repository.AccountRepository
import com.giant.module.auth.repository.RefreshTokenRepository
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class AuthService(
    private val accountRepository: AccountRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val jwtUtil: JwtUtil,
    private val authUtil: AuthUtil
) {
    private val log = KotlinLogging.logger {}

    private fun issueTokensAndSaveRefreshToken(info: SignInVo, isAuto: Boolean, account: Account): SignInResponseDto {
        authUtil.validateAuthRole(info)

        val accessClaims = jwtProvider.generateAccessToken(
            userId = info.accountId.toString(),
            accountRole = info.authRole.toString()
        )

        val refreshClaims = jwtProvider.generateRefreshToken(
            userId = info.accountId.toString()
        )

        val refreshTokenExpiresAtMs = refreshClaims.expiresAtMs
        val refreshTokenExpiresAtDate = Instant.ofEpochMilli(refreshTokenExpiresAtMs)

        refreshTokenRepository.save(
            RefreshToken.create(account, refreshClaims.token, refreshTokenExpiresAtDate)
        )

        return authUtil.buildSignInResponse(
            info, accessToken = accessClaims.token, accessTokenExpiresAtMs = accessClaims.expiresAtMs,
            refreshToken = refreshClaims.token, isAuto,
            refreshTokenExpiresAtMs
        )
    }

    @Transactional
    fun signIn(requestDto: SignInRequestDto): SignInResponseDto {
        val info = accountRepository.findSignInInfoByUserName(requestDto.userName)
            .orElseThrow { CustomException(ResponseCode.LOGIN_ERROR) }
        val account = accountRepository.findByIdOrNull(info.accountId)
            ?: throw CustomException(ResponseCode.LOGIN_ERROR)

        if (!passwordEncoder.matches(requestDto.password, info.passwordHash)) {
            throw CustomException(ResponseCode.LOGIN_ERROR)
        }

        log.info { "Sign In successfully for account ID: $info.accountId" }

        return issueTokensAndSaveRefreshToken(info, requestDto.isAuto, account)
    }
}