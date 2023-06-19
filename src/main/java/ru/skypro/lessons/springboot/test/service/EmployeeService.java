package ru.skypro.lessons.springboot.test.service;

import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.model.Employee;

import java.util.List;

public interface EmployeeService {

    void addEmployee(EmployeeDTO employeeDTO);
    void updateEmployee (EmployeeDTO employeeDTO);
    Employee findEmployee(int id);
    void deleteEmployee(int id);
    List<EmployeeDTO> findAllEmployeesHigherThanSalary(int testSalary);

    List <Employee> findAllEmployeesWithHighestSalary();
    List<Employee> findByPosition (String searchPosition);
    List<EmployeeView> getFullInfo(int id);
    List<Employee> getEmployeeWithPaging(int pageIndex, int eployeesInPage);
    List<Employee> getAllEmployees();


}
