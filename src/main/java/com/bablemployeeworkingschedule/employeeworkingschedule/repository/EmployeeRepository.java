package com.bablemployeeworkingschedule.employeeworkingschedule.repository;

import com.bablemployeeworkingschedule.employeeworkingschedule.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEmployeeId(String employeeId);
}
