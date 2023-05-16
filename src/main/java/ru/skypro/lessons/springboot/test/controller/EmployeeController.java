package ru.skypro.lessons.springboot.test.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.test.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    @GetMapping("/salary/sum")
    public double getSumOfSalary() {
       return employeeService.getSumOfSalary();
    }
    @GetMapping("/salary/min")
    public String getMinSalary() {
        return employeeService.getMinSalary();
    }
    @GetMapping("/salary/max")
    public String getMaxSalary() {
        return employeeService.getMaxSalary();
    }
    @GetMapping("/high-salary")
    public String getHighSalary() {
        return employeeService.getHighSalary();
    }

}
