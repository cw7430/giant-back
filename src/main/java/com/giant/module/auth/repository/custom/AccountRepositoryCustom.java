package com.giant.module.auth.repository.custom;

import com.giant.module.auth.vo.SignInVo;

import java.util.Optional;

public interface AccountRepositoryCustom {
    Optional<SignInVo> findSignInInfoByUserName(String userName);
    Optional<SignInVo> findRefreshInfoByAccountId(Long accountId);
}
