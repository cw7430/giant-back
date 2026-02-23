package com.giant.module.auth.repository

import com.giant.module.auth.entity.AuthView
import org.springframework.data.jpa.repository.JpaRepository

interface AuthViewRepository : JpaRepository<AuthView, Long>