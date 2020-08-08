package com.bablemployeeworkingschedule.employeeworkingschedule.Entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "work_schedule")
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_at")
    private LocalDate dateAt;

    @Column(name = "started_at")
    private String startedAt;

    @Column(name = "end_at")
    private String endAt;

    @Column(name = "shift_name")
    private String shiftName;

    @ManyToMany(fetch= FetchType.LAZY,
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "employee_schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees = new HashSet<>();

    public WorkSchedule() {
    }

    public WorkSchedule(LocalDate dateAt, String startedAt, String endAt,String shiftName) {
        this.dateAt = dateAt;
        this.startedAt = startedAt;
        this.endAt = endAt;
        this.shiftName = shiftName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAt() {
        return dateAt;
    }

    public void setDateAt(LocalDate dateAt) {
        this.dateAt = dateAt;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkSchedule that = (WorkSchedule) o;
        return Objects.equals(dateAt, that.dateAt) &&
                Objects.equals(startedAt, that.startedAt) &&
                Objects.equals(endAt, that.endAt) &&
                Objects.equals(shiftName, that.shiftName) &&
                Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateAt, startedAt, endAt, shiftName, employees);
    }

    @Override
    public String toString() {
        return "WorkSchedule{" +
                "id=" + id +
                ", dateAt=" + dateAt +
                ", startedAt='" + startedAt + '\'' +
                ", endAt='" + endAt + '\'' +
                ", shiftName='" + shiftName + '\'' +
                '}';
    }
}
