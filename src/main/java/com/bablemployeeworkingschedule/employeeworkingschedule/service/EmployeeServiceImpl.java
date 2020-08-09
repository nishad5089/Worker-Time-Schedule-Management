package com.bablemployeeworkingschedule.employeeworkingschedule.service;

import com.bablemployeeworkingschedule.employeeworkingschedule.Entity.Employee;
import com.bablemployeeworkingschedule.employeeworkingschedule.Entity.WorkSchedule;
import com.bablemployeeworkingschedule.employeeworkingschedule.dto.EmployeeWorkScheduleDto;
import com.bablemployeeworkingschedule.employeeworkingschedule.repository.EmployeeRepository;
import com.bablemployeeworkingschedule.employeeworkingschedule.repository.WorkScheduleRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Service
public class EmployeeServiceImpl implements FileService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Override
    public List<EmployeeWorkScheduleDto> saveData(List<EmployeeWorkScheduleDto> employeeWorkScheduleDtos) {
        for (EmployeeWorkScheduleDto employeeWorkScheduleDto : employeeWorkScheduleDtos) {
            Employee employeeFromDb = employeeRepository.findByEmployeeId(employeeWorkScheduleDto.getEmployeeId());
            WorkSchedule findWorkScheduleAtDb = workScheduleRepository.findByDateAtAndShiftName(employeeWorkScheduleDto.getDateAt(),employeeWorkScheduleDto.getShiftName());
            if(employeeFromDb == null){
                Employee employee = new Employee(employeeWorkScheduleDto.getEmployeeId(),employeeWorkScheduleDto.getEmployeeName());
                if(findWorkScheduleAtDb == null){
                    WorkSchedule workSchedule = new WorkSchedule(employeeWorkScheduleDto.getDateAt(),employeeWorkScheduleDto.getStartAt(),employeeWorkScheduleDto.getEndAt(),employeeWorkScheduleDto.getShiftName());
                    employee.getWorkSchedules().add(workSchedule);
                }else {
                    employee.getWorkSchedules().add(findWorkScheduleAtDb);
                }
                employeeRepository.save(employee);
            }else{
                if(findWorkScheduleAtDb == null) {
                    WorkSchedule workSchedule = new WorkSchedule(employeeWorkScheduleDto.getDateAt(),employeeWorkScheduleDto.getStartAt(),employeeWorkScheduleDto.getEndAt(),employeeWorkScheduleDto.getShiftName());
                    employeeFromDb.getWorkSchedules().add(workSchedule);

                }else {
                    employeeFromDb.getWorkSchedules().add(findWorkScheduleAtDb);
                }
                employeeRepository.save(employeeFromDb);
            }

        }
        return employeeWorkScheduleDtos;
    }

    @Override
    public List<EmployeeWorkScheduleDto> readDataFromUploadFile(MultipartFile file) {
        Workbook workbook = getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        rows.next();
        List<EmployeeWorkScheduleDto> employeeWorkScheduleDtos = null;
        while (rows.hasNext()){
            Row row = rows.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            //Getting the default zone id
            ZoneId defaultZoneId = ZoneId.systemDefault();
            //Converting the date to Instant
            if(row.getCell(1).getDateCellValue().toString() != null){
                Instant instant = row.getCell(1).getDateCellValue().toInstant();
                LocalDate date =  instant.atZone(defaultZoneId).toLocalDate();
                Date date1 = row.getCell(3).getDateCellValue();
                Date date2 = row.getCell(4).getDateCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                String s = sdf.format(date1);
                String s1 = sdf.format(date2);
                System.out.println(s);
                int employeeId = (int) row.getCell(0).getNumericCellValue();

                EmployeeWorkScheduleDto employeeWorkScheduleDto = new EmployeeWorkScheduleDto(Integer.toString(employeeId),date,row.getCell(2).toString(),s,s1);
                if(employeeWorkScheduleDtos == null){
                    employeeWorkScheduleDtos = new ArrayList<>();
                }
                employeeWorkScheduleDtos.add(employeeWorkScheduleDto);
                System.out.println(employeeWorkScheduleDto);
            }

        }
        return employeeWorkScheduleDtos;
    }

    @Override
    public List<EmployeeWorkScheduleDto> invalidSchedules(List<EmployeeWorkScheduleDto> employeeWorkScheduleDtos) {
        List<EmployeeWorkScheduleDto> employeeDtos = null;
        Map<String, Set<LocalDate>> map =  new HashMap<>();
        Map<Integer,Integer> count = new HashMap<>();
        Set<LocalDate> localDates = null;
        int key=0;
        for (EmployeeWorkScheduleDto sche: employeeWorkScheduleDtos){
            int dateCount =0;
            if(!map.containsKey(sche.getEmployeeId())) {
                localDates = new TreeSet<>();
                localDates.add(sche.getDateAt());
                map.put(sche.getEmployeeId(),localDates);
                count.put(++key,++dateCount);
            }else {
                Set<LocalDate> localDates1 = map.get(sche.getEmployeeId());
                if(!localDates1.contains(sche.getDateAt())) {
                    localDates1.add(sche.getDateAt());
                    map.put(sche.getEmployeeId(),localDates1);
                    count.put(++key,++dateCount);
                }else {
                    count.put(key,count.get(key) + 1);
                }
            }
            if(count.get(key) >= 3) {
                System.out.println(sche);
                if(employeeDtos == null){
                    employeeDtos = new ArrayList<>();
                }
                employeeDtos.add(sche);
            }
        }
        return employeeDtos;
    }

    @Override
    public List<EmployeeWorkScheduleDto> invalidHolidaySchedules(List<EmployeeWorkScheduleDto> employeeWorkScheduleDtos) {
        List<EmployeeWorkScheduleDto> employeeDtos = null;
        Map<String, Set<LocalDate>> map =  new HashMap<>();
        Set<LocalDate> localDates = null;
        for (EmployeeWorkScheduleDto sche: employeeWorkScheduleDtos){
            if(!map.containsKey(sche.getEmployeeId())) {
                localDates = new TreeSet<>();
                localDates.add(sche.getDateAt());
                map.put(sche.getEmployeeId(),localDates);
            }else {
                Set<LocalDate> localDates1 = map.get(sche.getEmployeeId());
                if(!localDates1.contains(sche.getDateAt())) {
                    localDates1.add(sche.getDateAt());
                    map.put(sche.getEmployeeId(),localDates1);
                }
            }
            if(localDates.size() % 7 == 0){
                if(employeeDtos == null){
                    employeeDtos = new ArrayList<>();
                }
                List<LocalDate> listDates = new ArrayList<>(localDates);
                Period period = null;
                int workingDayCount =0;
                for (int i=localDates.size() / 7-1;i < listDates.size()-1;i++) {
                    period = Period.between(listDates.get(i),listDates.get(i+1));
                    if(period.getDays() == 1) {
                        workingDayCount++;
                    }else {
                        workingDayCount=0;
                    }
                }
                if(workingDayCount % 6 == 0){
                    System.out.println("weekly days"+sche);
                    employeeDtos.add(sche);
                }
            }
        }
        return employeeDtos;
    }

    private Workbook getWorkBook(MultipartFile file) {
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            if (extension.equalsIgnoreCase("xlsx")){
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if(extension.equalsIgnoreCase("xls")){
                workbook = new HSSFWorkbook(file.getInputStream());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workbook;
    }
}
