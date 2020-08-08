package com.bablemployeeworkingschedule.employeeworkingschedule.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class EmployeeWorkScheduleDto {
    private String employeeId;
    private LocalDate dateAt;
    private String employeeName;
    private String startAt;
    private String endAt;
    private String shiftName;
    private MultipartFile file;
    public EmployeeWorkScheduleDto() {
    }

    public EmployeeWorkScheduleDto(String employeeId, LocalDate dateAt, String employeeName, String startAt, String endAt) {
        this.employeeId = employeeId;
        this.dateAt = dateAt;
        this.employeeName = employeeName;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDateAt() {
        return dateAt;
    }

    public void setDateAt(LocalDate dateAt) {
        this.dateAt = dateAt;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getShiftName() {
        String shift = "";
        String dayname = dateAt.getDayOfWeek().toString().toLowerCase();
        if(startAt.startsWith("08")){
            shift = "Morning";
        }else if(startAt.startsWith("02")){
            shift = "Evining";
        }else if(startAt.startsWith("09")){
            shift = "Night";
        }else {
            System.out.println("Invalid shift");
        }
        shiftName = dayname.substring(0,1).toUpperCase() + dayname.substring(1)+"-"+ shift;
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "EmployeeWorkScheduleDto{" +
                "employeeId='" + employeeId + '\'' +
                ", dateAt=" + dateAt +
                ", employeeName='" + employeeName + '\'' +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                '}';
    }
}
