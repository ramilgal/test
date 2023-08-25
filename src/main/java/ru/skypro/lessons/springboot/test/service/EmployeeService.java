package ru.skypro.lessons.springboot.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.model.Report;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    void addEmployee(EmployeeDTO employeeDTO);
    void addEmployees(List<EmployeeDTO> employeeDTO);
    void updateEmployee (EmployeeDTO employeeDTO);
    Employee findEmployee(int id);
    void deleteEmployee(int id);
    List<EmployeeDTO> findAllEmployeesHigherThanSalary(int testSalary);

    List <Employee> findAllEmployeesWithHighestSalary();
    List<Employee> findByPosition (String searchPosition);
    List<EmployeeView> getFullInfo(int id);
    List<Employee> getEmployeeWithPaging(int pageIndex, int eployeesInPage);
    List<Employee> getAllEmployees();



    void upload(MultipartFile file);
    Integer report() throws JsonProcessingException;
     String getReportById(Integer id) throws IOException;


    //Старые методы:
//    int getSumOfSalary();
//    int getMinSalary();
//    int getMaxSalary();
//    String getHighSalary();
//    int getAverageSalary();
}
