package com.giant.module.auth

import com.giant.common.api.exception.CustomException
import com.giant.common.api.type.ResponseCode
import com.giant.module.auth.dto.response.SignInResponseDto
import com.giant.module.auth.dto.vo.SignInVo
import com.giant.module.auth.entity.type.AuthRole
import org.springframework.stereotype.Component

@Component
class AuthUtil {
    fun validateAuthRole(info: SignInVo) {
        if (info.authRole == AuthRole.LEFT) {
            throw CustomException(ResponseCode.FORBIDDEN)
        }
    }

    fun buildSignInResponse(
        info: SignInVo,
        accessToken: String,
        accessTokenExpiresAtMs: Long,
        refreshToken: String,
        isAuto: Boolean,
        refreshTokenExpiresAtMs: Long
    ): SignInResponseDto {
        return SignInResponseDto(
            accessToken, accessTokenExpiresAtMs, refreshToken, refreshTokenExpiresAtMs,
            isAuto, info.employeeCode, info.employeeName, info.authRole,
            info.employeeRole, department = info.departmentCode,
            team = info.teamCode, position = info.positionCode
        )
    }
}