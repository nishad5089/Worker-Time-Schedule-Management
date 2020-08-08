package com.bablemployeeworkingschedule.employeeworkingschedule.service;

import com.bablemployeeworkingschedule.employeeworkingschedule.dto.EmployeeWorkScheduleDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<EmployeeWorkScheduleDto> saveData(List<EmployeeWorkScheduleDto> employeeWorkScheduleDtos);

    List<EmployeeWorkScheduleDto> readDataFromUploadFile(MultipartFile file);

    List<EmployeeWorkScheduleDto> invalidSchedules(List<EmployeeWorkScheduleDto> employeeWorkScheduleDtos);

    List<EmployeeWorkScheduleDto> invalidHolidaySchedules(List<EmployeeWorkScheduleDto> employeeWorkScheduleDtos);
}
