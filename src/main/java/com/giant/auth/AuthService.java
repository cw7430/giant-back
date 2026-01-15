package com.giant.auth;

import com.giant.auth.dto.SignInDto;
import com.giant.auth.dto.request.*;
import com.giant.auth.dto.response.SignInResponseDto;
import com.giant.auth.entity.Account;
import com.giant.auth.entity.RefreshToken;
import com.giant.auth.repository.AccountRepository;
import com.giant.auth.repository.RefreshTokenRepository;
import com.giant.common.api.exception.CustomException;
import com.giant.common.api.type.ResponseCode;
import com.giant.common.config.security.JwtProvider;
import com.giant.common.config.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;

    private SignInResponseDto issueTokensAndSaveRefreshToken(
            SignInDto info,
            boolean isAuto,
            Account account
    ) {
        authUtil.validateAccountRole(info);

        String accessToken =
                jwtProvider.generateAccessToken(info.accountId(), info.accountRoleId());

        String refreshToken =
                jwtProvider.generateRefreshToken(info.accountId(), isAuto);

        Instant refreshTokenExpiresAt =
                authUtil.extractRefreshTokenExpiresAt(refreshToken);

        refreshTokenRepository.save(
                RefreshToken.create(account, refreshToken, refreshTokenExpiresAt)
        );

        return authUtil.buildSignInResponse(
                info, accessToken, refreshToken, isAuto
        );
    }

    @Transactional
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        SignInDto info = accountRepository.findSignInInfoByUserName(signInRequestDto.userName());
        Account account = accountRepository.findById(info.accountId())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));

        if (!passwordEncoder.matches(signInRequestDto.password(), info.passwordHash())) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        log.info("Sign In successfully for account ID: {}", info.accountId());

        return issueTokensAndSaveRefreshToken(info, signInRequestDto.isAuto(), account);
    }

    @Transactional
    public void signOut(SignOutRequestDto signOutRequestDto) {
        if (signOutRequestDto.refreshToken() == null || signOutRequestDto.refreshToken().isBlank()) return;
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(signOutRequestDto.refreshToken())
                .orElse(null);
        if (refreshToken != null) {
            refreshTokenRepository.deleteByToken(refreshToken.getToken());
            log.info("Sign Out successfully for account ID: {}", refreshToken.getAccount().getAccountId());
        }
    }

    @Transactional
    public SignInResponseDto refreshAccessToken(
            HttpServletRequest request,
            RefreshRequestDto refreshRequestDto
    ) {
        String prevRefreshToken = jwtUtil.extractToken(request);
        Long accountId = jwtUtil.extractUserIdFromRefreshToken(prevRefreshToken);

        SignInDto info = accountRepository.findRefreshInfoByAccountId(accountId);
        Account account = accountRepository.findById(info.accountId())
                .orElseThrow(() -> new CustomException(ResponseCode.UNAUTHORIZED));

        refreshTokenRepository.deleteByToken(prevRefreshToken);

        log.info("Access token refreshed successfully for account ID: {}", accountId);

        return issueTokensAndSaveRefreshToken(info, refreshRequestDto.isAuto(), account);
    }


    public void checkUserNameDuplicate(CheckUserRequestDto checkUserRequestDto) {
        if (accountRepository.existsByUserName(checkUserRequestDto.userName()))
            throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);

        log.info("User name is available: {}", checkUserRequestDto.userName());
    }

    @Transactional
    public void updateAccount(
            HttpServletRequest request,
            UpdateAccountRequestDto updateAccountRequestDto
    ) {
        String token = jwtUtil.extractToken(request);
        Long accountId = jwtUtil.extractUserIdFromAccessToken(token);

        if (accountRepository.existsByUserName(updateAccountRequestDto.userName()))
            throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);

        Account account = accountRepository
                .findById(accountId).orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));

        if (!passwordEncoder.matches(
                updateAccountRequestDto.password(), account.getPasswordHash()
        )) throw new CustomException(ResponseCode.UNAUTHORIZED);

        Account updatedAccount = account.updateAccount(
                updateAccountRequestDto.userName(),
                updateAccountRequestDto.phoneNumber(),
                updateAccountRequestDto.email()
        );

        log.info("Account information updated successfully. Updated account ID: {}", updatedAccount.getAccountId());
    }

    @Transactional
    public void updatePassword(
            HttpServletRequest request,
            UpdatePasswordRequestDto updatePasswordRequestDto
    ) {
        String token = jwtUtil.extractToken(request);
        Long accountId = jwtUtil.extractUserIdFromAccessToken(token);

        Account account = accountRepository
                .findById(accountId).orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));

        if (!passwordEncoder.matches(
                updatePasswordRequestDto.prevPassword(), account.getPasswordHash()
        )) throw new CustomException(ResponseCode.UNAUTHORIZED);

        Account updatedAccount = account.updatePassword(
                updatePasswordRequestDto.newPassword()
        );

        log.info("Password updated successfully. Updated account ID: {}", updatedAccount.getAccountId());
    }
}
