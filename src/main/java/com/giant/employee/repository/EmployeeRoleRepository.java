package com.giant.employee.repository;

import com.giant.employee.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {
}
