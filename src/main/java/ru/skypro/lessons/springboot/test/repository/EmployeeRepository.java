package ru.skypro.lessons.springboot.test.repository;

import ru.skypro.lessons.springboot.test.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAllEmployees();
    void deleteEmployee(int id);
    List<Employee> findEmployee(int id);
}
