package ru.skypro.lessons.springboot.test.service;

import ru.skypro.lessons.springboot.test.model.Employee;

import java.util.List;

public interface EmployeeService {
    int getSumOfSalary();
    String getMinSalary();
    String getMaxSalary();
    String getHighSalary();

    void addEmployee(List<Employee> employee);
    void updateEmployee (Employee employee);
    List<Employee> findEmployee(int id);
    void deleteEmployee(int id);
    List<Employee> findAllEmployeesHigherThanSalary(int salary);



}
