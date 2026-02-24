package com.giant.module.auth.repository

import com.giant.module.auth.entity.AuthView
import com.giant.module.auth.repository.custom.AuthViewRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface AuthViewRepository : JpaRepository<AuthView, Long>, AuthViewRepositoryCustom {
    fun existsByUserName(userName: String): Boolean
}