package com.giant.module.employee.repository

import com.giant.module.employee.entity.Position
import org.springframework.data.jpa.repository.JpaRepository

interface PositionRepository : JpaRepository<Position, Long>