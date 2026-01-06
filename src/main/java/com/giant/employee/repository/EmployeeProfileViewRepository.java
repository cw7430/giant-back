package com.giant.employee.repository;

import com.giant.employee.entity.EmployeeProfileView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProfileViewRepository extends JpaRepository<EmployeeProfileView, Long> {
}
