package com.giant.module.auth

import com.giant.BaseIntegrationTest
import com.giant.common.api.exception.CustomException
import com.giant.module.auth.dto.request.SignInRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class AuthServiceTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var authService: AuthService

    @Test
    @DisplayName("로그인 성공 테스트")
    fun signInSuccess() {
        // given
        val request = SignInRequestDto(
            userName = "EMP001",
            password = "0000",
            isAuto = false
        )

        // when
        val response = authService.signIn(request)

        // then
        assertThat(response.accessToken).isNotBlank()
        assertThat(response.refreshToken).isNotBlank()
        assertThat(response.employeeName).isEqualTo("이사장")
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    fun signInFailWithWrongPassword() {
        // given
        val request = SignInRequestDto(
            userName = "EMP001",
            password = "wrongpassword",
            isAuto = false
        )

        // when & then
        assertThatThrownBy { authService.signIn(request) }
            .isInstanceOf(CustomException::class.java)
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    fun signInFailWithNonExistentUser() {
        // given
        val request = SignInRequestDto(
            userName = "NOTEXIST",
            password = "0000",
            isAuto = false
        )

        // when & then
        assertThatThrownBy { authService.signIn(request) }
            .isInstanceOf(CustomException::class.java)
    }

    @Test
    @DisplayName("자동 로그인 옵션 테스트")
    fun signInWithAutoLogin() {
        // given
        val request = SignInRequestDto(
            userName = "EMP001",
            password = "0000",
            isAuto = true
        )

        // when
        val response = authService.signIn(request)

        // then
        assertThat(response.accessToken).isNotBlank()
        assertThat(response.refreshToken).isNotBlank()
    }
}