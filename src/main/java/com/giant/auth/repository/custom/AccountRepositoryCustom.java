package com.giant.auth.repository.custom;

import com.giant.auth.dto.SignInDto;

import java.util.Optional;

public interface AccountRepositoryCustom {
    Optional<SignInDto> findSignInInfoByUserName(String userName);
    Optional<SignInDto> findRefreshInfoByAccountId(Long accountId);
}
