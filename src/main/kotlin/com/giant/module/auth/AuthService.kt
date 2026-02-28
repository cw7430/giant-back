package com.giant.module.auth

import com.giant.common.api.exception.CustomException
import com.giant.common.api.type.ResponseCode
import com.giant.common.config.security.JwtProvider
import com.giant.common.config.security.JwtUtil
import com.giant.module.auth.dto.request.*
import com.giant.module.auth.dto.response.SignInResponseDto
import com.giant.module.auth.dto.vo.SignInVo
import com.giant.module.auth.entity.Account
import com.giant.module.auth.entity.RefreshToken
import com.giant.module.auth.repository.AccountRepository
import com.giant.module.auth.repository.AuthViewRepository
import com.giant.module.auth.repository.RefreshTokenRepository
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class AuthService(
    private val accountRepository: AccountRepository,
    private val authViewRepository: AuthViewRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val jwtUtil: JwtUtil,
    private val authUtil: AuthUtil
) {
    private val log = KotlinLogging.logger {}

    private fun issueTokensAndBuild(
        info: SignInVo,
        isAuto: Boolean,
        account: Account,
    ): SignInResponseDto {
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
            RefreshToken.create(
                account,
                token = refreshClaims.token,
                expiresAt = refreshTokenExpiresAtDate
            )
        )

        return authUtil.buildSignInResponse(
            info, accessToken = accessClaims.token, accessTokenExpiresAtMs = accessClaims.expiresAtMs,
            refreshToken = refreshClaims.token, isAuto,
            refreshTokenExpiresAtMs
        )
    }

    @Transactional
    fun signIn(requestDto: SignInRequestDto): SignInResponseDto {
        val info = authViewRepository.findSignInInfoByUserName(requestDto.userName)
            ?: throw CustomException(ResponseCode.LOGIN_ERROR)
        val account = accountRepository.findByIdOrNull(info.accountId)
            ?: throw CustomException(ResponseCode.LOGIN_ERROR)

        if (!passwordEncoder.matches(requestDto.password, info.passwordHash)) {
            throw CustomException(ResponseCode.LOGIN_ERROR)
        }

        log.info { "Sign In successfully for account ID: $info.accountId" }

        return issueTokensAndBuild(
            info = info,
            isAuto = requestDto.isAuto,
            account = account
        )
    }

    @Transactional
    fun refresh(request: HttpServletRequest, requestDto: RefreshRequestDto): SignInResponseDto {
        val prevRefreshToken = jwtUtil.extractToken(request)
        val accountId = jwtUtil.extractUserIdFromRefreshToken()

        val refreshTable = refreshTokenRepository.findByToken(prevRefreshToken)
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)
        val info = authViewRepository.findRefreshInfoByAccountId(accountId)
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)
        refreshTokenRepository.delete(refreshTable)
        val account = accountRepository.findByIdOrNull(info.accountId)
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)

        log.info { "Refresh successfully for account ID: $info.accountId" }

        return issueTokensAndBuild(
            info = info,
            isAuto = requestDto.isAuto,
            account = account
        )
    }

    @Transactional
    fun signOut(requestDto: SignOutRequestDto) {
        if (requestDto.refreshToken == null) return
        val refreshTable = refreshTokenRepository.findByToken(requestDto.refreshToken) ?: return
        refreshTokenRepository.delete(refreshTable)

        log.info { "Sign Out successfully for account ID: ${refreshTable.account.accountId}" }
    }

    fun checkUserDuplicate(requestDto: CheckUserRequestDto) {
        val isDuplicate = authViewRepository.existsByUserName(requestDto.userName)
        if (isDuplicate) {
            throw CustomException(ResponseCode.DUPLICATE_RESOURCE)
        }
        log.info { "Check User successfully for User Name: ${requestDto.userName}" }
    }

    @Transactional
    fun updateUserName(requestDto: UpdateUserNameRequestDto) {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val isDuplicate = authViewRepository.existsByUserName(requestDto.userName)
        if (isDuplicate) {
            throw CustomException(ResponseCode.DUPLICATE_RESOURCE)
        }
        val account = accountRepository.findByIdOrNull(accountId)
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)
        if (!passwordEncoder.matches(requestDto.password, account.passwordHash)) {
            throw CustomException(ResponseCode.PASSWORD_ERROR)
        }
        val result = account.updateUserName(requestDto.userName)
        log.info { "Update User Name successfully for account ID: ${result.accountId}" }
    }

    @Transactional
    fun updatePassword(requestDto: UpdatePasswordRequestDto) {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val account = accountRepository.findByIdOrNull(accountId)
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)
        if (!passwordEncoder.matches(requestDto.prevPassword, account.passwordHash)) {
            throw CustomException(ResponseCode.PASSWORD_ERROR)
        }
        if (passwordEncoder.matches(requestDto.newPassword, account.passwordHash)) {
            throw CustomException(ResponseCode.DUPLICATE_RESOURCE)
        }
        val newPasswordHash = passwordEncoder.encode(requestDto.newPassword)!!

        val result = account.updatePassword(newPasswordHash)
        log.info { "Update Password successfully for account ID: ${result.accountId}" }
    }

    @Transactional
    fun updateAccount(requestDto: UpdateAccountRequestDto) {
        val accountId = jwtUtil.extractUserIdFromAccessToken()
        val account = accountRepository.findByIdOrNull(accountId)
            ?: throw CustomException(ResponseCode.UNAUTHORIZED)
        if (!passwordEncoder.matches(requestDto.password, account.passwordHash)) {
            throw CustomException(ResponseCode.PASSWORD_ERROR)
        }
        requestDto.phoneNumber?.let { account.phoneNumber = it }
        requestDto.email?.let { account.email = it }
        log.info { "Update User successfully for account ID: $accountId" }
    }
}