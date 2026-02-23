package com.giant.module.employee.repository

import com.giant.module.employee.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository : JpaRepository<Team, Long>