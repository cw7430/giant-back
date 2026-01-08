package com.giant.auth;

import com.giant.auth.dto.SignInDto;
import com.giant.auth.dto.request.CheckUserRequestDto;
import com.giant.auth.dto.request.RefreshRequestDto;
import com.giant.auth.dto.request.SignInRequestDto;
import com.giant.auth.dto.response.SignInResponseDto;
import com.giant.auth.repository.AccountRepository;
import com.giant.common.api.exception.CustomException;
import com.giant.common.api.type.ResponseCode;
import com.giant.common.config.security.JwtProvider;
import com.giant.common.config.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;

    public SignInResponseDto signIn(HttpServletResponse response, SignInRequestDto signInRequestDto) {
        SignInDto signInInfo = accountRepository.findSignInInfoByUserName(signInRequestDto.userName());

        if (signInInfo == null || !passwordEncoder.matches(
                signInRequestDto.password(), signInInfo.passwordHash()
        )
        ) throw new CustomException(ResponseCode.LOGIN_ERROR);

        authUtil.validateAccountRole(signInInfo);

        String accessToken = authUtil.generateAndSetTokens(response, signInInfo, signInRequestDto.isAuto());

        return authUtil.createSignInResponse(signInInfo, accessToken);
    }

    public void signOut(HttpServletResponse response) {
        jwtUtil.removeRefreshTokenFromCookie(response);
    }

    public SignInResponseDto refreshAccessToken(
            HttpServletRequest request,
            HttpServletResponse response,
            RefreshRequestDto refreshRequestDto
    ) {
        Long accountId = jwtUtil.extractUserIdFromRefreshToken(request);
        SignInDto refreshInfo = accountRepository.findRefreshInfoByAccountId(accountId);

        if (refreshInfo == null) throw new CustomException(ResponseCode.UNAUTHORIZED);
        authUtil.validateAccountRole(refreshInfo);

        String accessToken = jwtProvider.generateAccessToken(refreshInfo.accountId(), refreshInfo.accountRoleId());
        if (refreshRequestDto.isAuto()) {
            jwtUtil.addRefreshTokenToCookie(response, jwtProvider.generateRefreshToken(accountId, true), true);
        }

        return authUtil.createSignInResponse(refreshInfo, accessToken);
    }

    public void checkUserNameDuplicate(CheckUserRequestDto checkUserRequestDto) {
        if (accountRepository.existsByUserName(checkUserRequestDto.userName()))
            throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);
    }
}
