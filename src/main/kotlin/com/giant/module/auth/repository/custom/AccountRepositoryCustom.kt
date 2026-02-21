package com.giant.module.auth.repository.custom

import com.giant.module.auth.dto.vo.SignInVo

interface AccountRepositoryCustom {
    fun findSignInInfoByUserName(userName:String): SignInVo?
    fun findRefreshInfoByAccountId(accountId:Long): SignInVo?
}