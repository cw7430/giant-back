package com.giant.auth;

import com.giant.auth.dto.SignInDto;
import com.giant.auth.dto.request.CheckUserRequestDto;
import com.giant.auth.dto.request.RefreshRequestDto;
import com.giant.auth.dto.request.SignInRequestDto;
import com.giant.auth.dto.request.UpdateAccountInfoRequestDto;
import com.giant.auth.dto.response.SignInResponseDto;
import com.giant.auth.entity.Account;
import com.giant.auth.repository.AccountRepository;
import com.giant.common.api.exception.CustomException;
import com.giant.common.api.type.ResponseCode;
import com.giant.common.config.security.JwtProvider;
import com.giant.common.config.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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

        log.info("Sign In successfully for account ID: {}", signInInfo.accountId());

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

        log.info("Access token refreshed successfully for account ID: {}", accountId);

        return authUtil.createSignInResponse(refreshInfo, accessToken);
    }

    public void checkUserNameDuplicate(CheckUserRequestDto checkUserRequestDto) {
        if (accountRepository.existsByUserName(checkUserRequestDto.userName()))
            throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);

        log.info("User name is available: {}", checkUserRequestDto.userName());
    }

    @Transactional
    public void updateAccountInfo(
            HttpServletRequest request,
            UpdateAccountInfoRequestDto updateAccountInfoRequestDto
    ) {
        Long accountId = jwtUtil.extractUserIdFromAccessToken(request);

        if (accountRepository.existsByUserName(updateAccountInfoRequestDto.userName()))
            throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);

        Account account = accountRepository
                .findById(accountId).orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));

        if(!passwordEncoder.matches(
                updateAccountInfoRequestDto.password(), account.getPasswordHash()
        )) throw new CustomException(ResponseCode.UNAUTHORIZED);

        Account updatedAccount = account.updateAccountInfo(
                updateAccountInfoRequestDto.userName(),
                updateAccountInfoRequestDto.phoneNumber(),
                updateAccountInfoRequestDto.email()
        );

        log.info("Account information updated successfully. Updated account ID: {}", updatedAccount.getAccountId());
    }
}
