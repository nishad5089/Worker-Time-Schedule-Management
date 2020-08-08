package com.bablemployeeworkingschedule.employeeworkingschedule.repository;

import com.bablemployeeworkingschedule.employeeworkingschedule.Entity.Employee;
import com.bablemployeeworkingschedule.employeeworkingschedule.Entity.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule,Long> {
    WorkSchedule findByDateAtAndShiftName(LocalDate date,String shiftName);
}
