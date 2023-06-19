package ru.skypro.lessons.springboot.test.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.repository.EmployeeRepository;

import java.util.*;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAllEmployeesWithHighestSalary() {
        return employeeRepository.findAllEmployeesWithHighestSalary();
    }

    @Override
    @SneakyThrows
    public List<Employee> findByPosition(String searchPosition) {
        if (searchPosition.isBlank()) {
            return employeeRepository.findAllEmployees();
        }else
            try {
                return employeeRepository.findEmployeeByPosition_Id(Integer.parseInt(searchPosition));

            } catch (NumberFormatException exception) {
                return employeeRepository.findEmployeeByPositionLike(searchPosition);
            }
    }
    //            if (searchPosition.matches("\\d+")); альтернатива

    @Override
    public List<EmployeeView> getFullInfo(int id) {
        return employeeRepository.getFullInfoByIdInProection(id);
    }

    @Override
    public List<Employee> getEmployeeWithPaging(int pageIndex, int eployeesInPage) {
        Page<Employee> page = employeeRepository.findAll(PageRequest.of(pageIndex, eployeesInPage));
        return page.stream()
                .toList();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();

    }
    //Старые методы с прошлой домашки:
    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTO.toEmployee();
        employeeRepository.save(employee);
    }

    @Override
    @SneakyThrows
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTO.toEmployee();
        employeeRepository.save(employee);
    }
    @Override
    @SneakyThrows
    public Employee findEmployee(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    @SneakyThrows
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }
    @Override
    public List<EmployeeDTO> findAllEmployeesHigherThanSalary(int testSalary) {
        List<Employee> employees = employeeRepository.findAllBySalaryGreaterThan(testSalary);
        List<EmployeeDTO> employeeDTO = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO employeeDTO1 = EmployeeDTO.fromEmployee(employee);
            employeeDTO.add(employeeDTO1);
        }
        return employeeDTO;
    }






//Старые методы:
//    @Override
//    public int getSumOfSalary() {
//    int sum = 0;
//        for (Employee employees: employeeRepository.getEmployeeList()
//             ) {sum = sum+employees.getSalary();
//        }
//        System.out.println("Сумма всех зарплат: " + sum);
//        return sum;
//    }
//
//    @Override
//    public String getMinSalary() {
//        int salaryMinimum = Integer.MAX_VALUE;
//        String person = null;
//        for (Employee employees : employeeRepository.getEmployeeList()) {
//            if (employees.getSalary() < salaryMinimum) {
//            salaryMinimum = employees.getSalary();
//            person=employees.getName();
//            }
//        }
//        return person+" "+salaryMinimum;
//    }
//
//    @Override
//    public String getMaxSalary() {
//        int salaryMaximum = 0;
//        String person = null;
//        for (Employee employees : employeeRepository.getEmployeeList()) {
//            if (employees.getSalary() > salaryMaximum) {
//                salaryMaximum = employees.getSalary();
//                person = employees.getName();
//            }
//        }
//        return person+" "+salaryMaximum;
//    }
//
//    @Override
//    public String getHighSalary() {
//        int sum = 0;
//        int averageSalary;
//        int salaryMaximum = 0;
//        String person = null;
//        for (Employee employees : employeeRepository.getEmployeeList()) {
//            sum = sum + employees.getSalary();
//        }
//        averageSalary = sum / employeeRepository.getEmployeeList().size();
//        for (Employee employees : employeeRepository.getEmployeeList()) {
//            if (employees.getSalary() > averageSalary) {
//                person = employees.getName();
//                salaryMaximum = employees.getSalary();
//            }
//        }
//        return person+" "+salaryMaximum;
//    }
}
