package ru.skypro.lessons.springboot.test.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/admin/employees")
@AllArgsConstructor
public class AdminController {
    private final EmployeeService employeeService;

    @GetMapping("/findAllEmployees")
    List<Employee> findAllEmployees(){
        return employeeService.getAllEmployees();
    }
}
