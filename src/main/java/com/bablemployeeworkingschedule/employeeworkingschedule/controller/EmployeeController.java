package com.bablemployeeworkingschedule.employeeworkingschedule.controller;

import com.bablemployeeworkingschedule.employeeworkingschedule.dto.EmployeeWorkScheduleDto;
import com.bablemployeeworkingschedule.employeeworkingschedule.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("employeeWorkSchedule", new EmployeeWorkScheduleDto());
        List<EmployeeWorkScheduleDto> employeeWorkScheduleDtos = (List<EmployeeWorkScheduleDto>) model.asMap().get("attr");
        String msg = (String) model.asMap().get("msg");
        String flag = (String) model.asMap().get("flag");
        model.addAttribute("attr", employeeWorkScheduleDtos);
        model.addAttribute("msg", msg);
        model.addAttribute("flag", flag);
        model.addAttribute("title", "Employee Working Schedule Management");
        return "uploadDocument";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@ModelAttribute EmployeeWorkScheduleDto employeeWorkScheduleDto, Model model, RedirectAttributes re) {
        List<EmployeeWorkScheduleDto> readDataFromUploadFile = fileService.readDataFromUploadFile(employeeWorkScheduleDto.getFile());
        List<EmployeeWorkScheduleDto> invalidSchedules = fileService.invalidSchedules(readDataFromUploadFile);
        if (invalidSchedules == null) {
            List<EmployeeWorkScheduleDto> invalidHolidaySchedules = fileService.invalidHolidaySchedules(readDataFromUploadFile);
            if (invalidHolidaySchedules == null) {
                List<EmployeeWorkScheduleDto> savedData = fileService.saveData(readDataFromUploadFile);
                if (savedData == null) {
                    re.addFlashAttribute("msg", "Data Not Saved");
                } else {
                    re.addFlashAttribute("msg", "Data Stored");
                    re.addFlashAttribute("flag", "true");
                    re.addFlashAttribute("attr", savedData);
                }
            } else {
                re.addFlashAttribute("flag", "false");
                re.addFlashAttribute("msg", "Invalid Working Schedule(Holiday)");
                re.addFlashAttribute("attr", invalidHolidaySchedules);
            }
        } else {
            re.addFlashAttribute("flag", "false");
            re.addFlashAttribute("msg", "Invalid Working Schedule");
            re.addFlashAttribute("attr", invalidSchedules);
        }

        return "redirect:/employee/";
    }

    @GetMapping("/report")
    public String form(Model model) {
        model.addAttribute("title","Weekly Working Report");
        return "reportForm";
    }

    @GetMapping("/deshboard")
    public String deshboard() {
        return "redirect:/";
    }

}
