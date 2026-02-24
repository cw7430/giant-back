package com.giant.module.auth.repository

import com.giant.module.auth.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long>