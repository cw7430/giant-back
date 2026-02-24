package com.giant.module.auth.repository.custom

import com.giant.module.auth.dto.vo.SignInVo

interface AuthViewRepositoryCustom {
    fun findSignInInfoByUserName(userName:String): SignInVo?
    fun findRefreshInfoByAccountId(accountId:Long): SignInVo?
}