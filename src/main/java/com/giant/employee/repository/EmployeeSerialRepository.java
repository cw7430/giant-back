package com.giant.employee.repository;

import com.giant.employee.entity.EmployeeSerial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeSerialRepository extends JpaRepository<EmployeeSerial, Integer> {
}
