package ru.skypro.lessons.springboot.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;
    private final ReportRepository reportRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Override
    public void
    upload(MultipartFile file) {
        logger.info("Was invoked method for upload report " + file);
        try {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (!"json".equals(extension)) {
                logger.error("There is no json files = " + extension);
                throw new IllegalJsonFileException();
            }List<EmployeeDTO> employeeDTOS = objectMapper.readValue(file.getBytes(), new TypeReference<>() {});
            addEmployees(employeeDTOS);
        } catch (IOException e) {
            logger.error("There is catching IOException = " + e);
            throw new IllegalJsonFileException();
        }
    }

    @Override
    public Integer report() throws JsonProcessingException {
        logger.info("Was invoked method for create report " );
        Path path = Paths.get("report" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss_SSSSSS")) +".json");
        List<ReportDTO> tempList = reportRepository.getReport();
        String json = objectMapper.writeValueAsString(tempList);
        writeTextToFile(json, path);
        Report report = new Report(path.toAbsolutePath().toString());
        return reportRepository.save(report).getId();
    }
    private void writeTextToFile(String json, Path path) {
        logger.info("Was invoked method for write json " + path +" to file " );
        try {
            Files.write(path, json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ioException) {
            logger.error("There is catching IOException in the method <writeTextToFile> = " + ioException);
            ioException.printStackTrace();
        }
    }

    @Override
    public String getReportById(Integer id) throws IOException {
        logger.error("There is no employee with id = " + id);
        return String.join("", Files.readAllLines(Path.of(reportRepository.findReportById(id).getFilePath())));
    }

    @Override
    public void
    addEmployees(List<EmployeeDTO> employeeDTO) {
        logger.info("Was invoked method for add Employees to list " + employeeDTO);
        employeeRepository.saveAll(employeeDTO
                .stream()
                .map(x->x.toEmployee())
                .toList());
    }


    @Override
    public List<Employee> findAllEmployeesWithHighestSalary() {
        logger.info("Was invoked method for findAllEmployeesWithHighestSalary  ");
        return employeeRepository.findAllEmployeesWithHighestSalary();
    }

    @Override
    @SneakyThrows
    public List<Employee> findByPosition(String searchPosition) {
        logger.info("Was invoked method for find employee By Position " + searchPosition);
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
        logger.info("Was invoked method for get employee FullInfo By id " + id);
        return employeeRepository.getFullInfoByIdInProection(id);
    }

    @Override
    public List<Employee> getEmployeeWithPaging(int pageIndex, int eployeesInPage) {
        logger.info("Was invoked method for getEmployeeWithPaging By pageIndex and  eployeesInPage" + pageIndex + eployeesInPage);
        Page<Employee> page = employeeRepository.findAll(PageRequest.of(pageIndex, eployeesInPage));
        return page.stream()
                .toList();
    }

    @Override
    public List<Employee> getAllEmployees() {
        logger.debug("Was invoked method for getAllEmployees ");
        return employeeRepository.findAllEmployees();

    }
    //Старые методы с прошлой домашки:
    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        logger.info("Was invoked method for add one Employee ");
        Employee employee = employeeDTO.toEmployee();
        employeeRepository.save(employee);
    }

    @Override
    @SneakyThrows
    public void updateEmployee(EmployeeDTO employeeDTO) {
        logger.info("Was invoked method for updateEmployee"  + employeeDTO);
        employeeRepository.save(employeeDTO.toEmployee());
    }
    @Override
    @SneakyThrows
    public Employee findEmployee(int id) {
        logger.info("Was invoked method for findEmployee by id"  + id);
        return employeeRepository.findById(id);
    }

    @Override
    @SneakyThrows
    public void deleteEmployee(int id) {
        logger.info("Was invoked method for delete Employee by id"  + id);
        employeeRepository.deleteById(id);
    }
    @Override
    public List<EmployeeDTO> findAllEmployeesHigherThanSalary(int testSalary) {
        logger.info("Was invoked method for find All Employees церш Higher Salary Than testSalary"  + testSalary);
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
//        logger.info("Was invoked method for get Sum Of Salary" );
//    int sum = 0;
//        for (Employee employees: employeeRepository.findAllEmployees())
//        {sum = sum+employees.getSalary();
//        }
//        System.out.println("Сумма всех зарплат: " + sum);
//        return sum;
//    }
//
//    @Override
//    public int getMinSalary() {
//        logger.info("Was invoked method for get  Min Salary" );
//        int salaryMinimum = Integer.MAX_VALUE;
//        for (Employee employees : employeeRepository.findAllEmployees()) {
//            if (employees.getSalary() < salaryMinimum) {
//            salaryMinimum = employees.getSalary();
//            }
//        }
//        return salaryMinimum;
//    }
//
//    @Override
//    public int getMaxSalary() {
//        logger.info("Was invoked method for get Max Salary" );
//        int salaryMaximum = 0;
//        for (Employee employees : employeeRepository.findAllEmployees()) {
//            if (employees.getSalary() > salaryMaximum) {
//                salaryMaximum = employees.getSalary();
//            }
//        }
//        return salaryMaximum;
//    }
//
//    @Override
//    public String getHighSalary() {
//        logger.info("Was invoked method for get High Salary" );
//        int sum = 0;
//        int averageSalary;
//        int salaryMaximum = 0;
//        String person = null;
//        for (Employee employees : employeeRepository.findAllEmployees()) {
//            sum = sum + employees.getSalary();
//        }
//        averageSalary = sum / employeeRepository.findAllEmployees().size();
//        for (Employee employees : employeeRepository.findAllEmployees()) {
//            if (employees.getSalary() > averageSalary) {
//                person = employees.getName();
//                salaryMaximum = employees.getSalary();
//            }
//        }
//        return person+" "+salaryMaximum;
//    }
//
//    @Override
//    public int getAverageSalary() {
//        logger.info("Was invoked method for get Average Salary" );
//        int averageSalary = getSumOfSalary()/employeeRepository.findAllEmployees().size();
//        return averageSalary;
//    }



}
