package ru.skypro.lessons.springboot.test.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.service.EmployeeService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    //Эти методы оставил с прошлой домашки:
//    @GetMapping("/salary/sum")
//    public double getSumOfSalary() {
//       return employeeService.getSumOfSalary();
//    }
//    @GetMapping("/salary/min")
//    public String getMinSalary() {
//        return employeeService.getMinSalary();
//    }
//    @GetMapping("/salary/max")
//    public String getMaxSalary() {
//        return employeeService.getMaxSalary();
//    }
//    @GetMapping("/high-salary")
//    public String getHighSalary() {
//        return employeeService.getHighSalary();
//    }





    @PostMapping
    void addEmployee(@RequestBody @Valid Employee employee){
        employeeService.addEmployee(employee);
    }
    @PutMapping
    void updateEmployee (Employee employee){
    employeeService.updateEmployee(employee);
    }
    @GetMapping("/{id}")
    Employee findEmployee(@PathVariable ("id") int id){
        return employeeService.findEmployee(id);
    }
    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable ("id") int id){
    employeeService.deleteEmployee(id);
    }
    @GetMapping
    List<Employee> findAllEmployeesHigherThanSalary(@RequestParam ("salary") int salary){
    return employeeService.findAllEmployeesHigherThanSalary(salary);
    }

}
