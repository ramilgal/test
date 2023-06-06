package ru.skypro.lessons.springboot.test.service;

import ru.skypro.lessons.springboot.test.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
//    int getSumOfSalary();
//    String getMinSalary();
//    String getMaxSalary();
//    String getHighSalary();

    void addEmployee(Employee employee);
    void updateEmployee (Employee employee);
    Employee findEmployee(int id);
    void deleteEmployee(int id);
    List<Employee> findAllEmployeesHigherThanSalary(int salary);



}
