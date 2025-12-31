package com.giant.auth

import com.giant.auth.dto.SignInDto
import com.giant.auth.dto.request.SignInRequestDto
import com.giant.auth.repository.AccountRepository
import com.giant.auth.repository.AccountRoleRepository
import com.giant.common.api.code.ResponseCode
import com.giant.common.api.exception.CustomException
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val accountRepository: AccountRepository,
    private val accountRoleRepository: AccountRoleRepository
) {

    fun signIn(signInRequestDto: SignInRequestDto): SignInDto {
        val signInInfo = accountRepository.findSignInInfoByUserName(signInRequestDto.userName)
        return signInInfo ?: throw CustomException(ResponseCode.UNAUTHORIZED)
    }
}