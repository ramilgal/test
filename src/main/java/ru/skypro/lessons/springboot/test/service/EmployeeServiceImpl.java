package ru.skypro.lessons.springboot.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.dto.ReportDTO;
import ru.skypro.lessons.springboot.test.exceptions.IllegalJsonFileException;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.model.Report;
import ru.skypro.lessons.springboot.test.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.test.repository.ReportRepository;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;
    private final ReportRepository reportRepository;
    @Override
    public void upload(MultipartFile file) {
        try {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (!"json".equals(extension)) {
                throw new IllegalJsonFileException();
            }List<EmployeeDTO> employeeDTOS = objectMapper.readValue(file.getBytes(), new TypeReference<>() {});
            addEmployees(employeeDTOS);
        } catch (IOException e) {
            throw new IllegalJsonFileException();
        }
    }

    @Override
    public Integer report() throws JsonProcessingException {
        List<ReportDTO> tempList = reportRepository.getReport();
        String json = objectMapper.writeValueAsString(tempList);
        Report report = new Report(json);
        return reportRepository.save(report).getId();
    }

    @Override
    public String getReportById(Integer id) {
        return reportRepository.findReportById(id);
    }




    @Override
    public void addEmployees(List<EmployeeDTO> employeeDTO) {
        employeeRepository.saveAll(employeeDTO
                .stream()
                .map(x->x.toEmployee())
                .toList());
    }


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
        employeeRepository.save(employeeDTO.toEmployee());
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
    @Override
    public int getSumOfSalary() {
    int sum = 0;
        for (Employee employees: employeeRepository.findAllEmployees())
        {sum = sum+employees.getSalary();
        }
        System.out.println("Сумма всех зарплат: " + sum);
        return sum;
    }

    @Override
    public int getMinSalary() {
        int salaryMinimum = Integer.MAX_VALUE;
        for (Employee employees : employeeRepository.findAllEmployees()) {
            if (employees.getSalary() < salaryMinimum) {
            salaryMinimum = employees.getSalary();
            }
        }
        return salaryMinimum;
    }

    @Override
    public int getMaxSalary() {
        int salaryMaximum = 0;
        for (Employee employees : employeeRepository.findAllEmployees()) {
            if (employees.getSalary() > salaryMaximum) {
                salaryMaximum = employees.getSalary();
            }
        }
        return salaryMaximum;
    }

    @Override
    public String getHighSalary() {
        int sum = 0;
        int averageSalary;
        int salaryMaximum = 0;
        String person = null;
        for (Employee employees : employeeRepository.findAllEmployees()) {
            sum = sum + employees.getSalary();
        }
        averageSalary = sum / employeeRepository.findAllEmployees().size();
        for (Employee employees : employeeRepository.findAllEmployees()) {
            if (employees.getSalary() > averageSalary) {
                person = employees.getName();
                salaryMaximum = employees.getSalary();
            }
        }
        return person+" "+salaryMaximum;
    }

    @Override
    public int getAverageSalary() {
        int averageSalary = getSumOfSalary()/employeeRepository.findAllEmployees().size();
        return averageSalary;
    }



}
