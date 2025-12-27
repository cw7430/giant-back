package com.giant.auth.repository

import com.giant.auth.entity.AccountRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRoleRepository: JpaRepository<AccountRole, Long> {
}