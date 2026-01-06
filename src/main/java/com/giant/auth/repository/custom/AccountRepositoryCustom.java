package com.giant.auth.repository.custom;

import com.giant.auth.dto.SignInDto;

public interface AccountRepositoryCustom {
    SignInDto findSignInInfoByUserName(String userName);
    SignInDto findRefreshInfoByAccountId(Long accountId);
}
