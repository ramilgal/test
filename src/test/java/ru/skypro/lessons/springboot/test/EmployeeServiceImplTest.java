package ru.skypro.lessons.springboot.test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.model.Report;
import ru.skypro.lessons.springboot.test.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.test.repository.ReportRepository;
import ru.skypro.lessons.springboot.test.service.EmployeeServiceImpl;
import ru.skypro.lessons.springboot.test.service.EmployeeView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.skypro.lessons.springboot.test.testdata.ConstantsOfEmployee.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ReportRepository reportRepository;



    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

//    public void
//    upload(MultipartFile file) {
//        logger.info("Was invoked method for upload report " + file);
//        try {
//            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
//            if (!"json".equals(extension)) {
//                logger.error("There is no json files = " + extension);
//                throw new IllegalJsonFileException();
//            }List<EmployeeDTO> employeeDTOS = objectMapper.readValue(file.getBytes(), new TypeReference<>() {});
//            addEmployees(employeeDTOS);
//        } catch (IOException e) {
//            logger.error("There is catching IOException = " + e);
//            throw new IllegalJsonFileException();
//        }
//    }



    @Test
    public void shouldAddEmployee_Correct_OK() {
        when(employeeRepository.save(EMPLOYEE)).thenReturn(any());
        employeeServiceImpl.addEmployee(EmployeeDTO.fromEmployee(EMPLOYEE));

        verify(employeeRepository, times(1)).save(EMPLOYEE);
    }

    @Test
    public void shouldGetReportAndSaveReport_ThenReturnCorrectReportList_ThenReturnCorrectReport() throws JsonProcessingException {
        when(reportRepository.getReport()).thenReturn(NEW_REPORT);
        when(reportRepository.save(any(Report.class))).thenReturn(REPORT);

        Integer actual = employeeServiceImpl.report();
        Integer expected = REPORT.getId();

        assertThat(expected).isEqualTo(actual);

        verify(reportRepository, times(1)).getReport();
        verify(reportRepository, times(1)).save(any(Report.class));
    }
    @Test
    public void shouldFindByPosition_AddEmptyString_ThenReturnCorrectList_ThenCallRepositoryOnce(){
       when(employeeRepository.findAllEmployees()).thenReturn(NEW_EMPLOYEES);
//       when(employeeRepository.findEmployeeByPosition_Id(anyInt())).thenReturn(anyList());
//       when(employeeRepository.findEmployeeByPositionLike(anyString())).thenReturn(anyList());

       List<Employee> actual = employeeServiceImpl.findByPosition("");
       List<Employee> expected = NEW_EMPLOYEES;

       assertThat(actual).isEqualTo(expected);

       verify(employeeRepository, times(1)).findAllEmployees();
       verify(employeeRepository, times(0)).findEmployeeByPosition_Id(anyInt());
       verify(employeeRepository, times(0)).findEmployeeByPositionLike(anyString());
    }

    @Test
    public void shouldFindEmployeeByPositionId_AddStringAsNumber_ThenReturnCorrectListOfEmployees(){
        when(employeeRepository.findEmployeeByPosition_Id(anyInt())).thenReturn(NEW_EMPLOYEES);

        Integer number = 1;
        List<Employee> actual = employeeServiceImpl.findByPosition(number.toString());
        List<Employee> expected = NEW_EMPLOYEES;

        assertThat(actual).isEqualTo(expected);

        verify(employeeRepository, times(1)).findEmployeeByPosition_Id(number);
        verify(employeeRepository, times(0)).findAllEmployees();
        verify(employeeRepository, times(0)).findEmployeeByPositionLike(anyString());
    }
//????????????????????:
    @Test
    public void shouldFindEmployeeByPositionLike_NumberFormatException_AddSearchPosition_ThenReturnCorrectListOfEmployees(){
        when(employeeRepository.findEmployeeByPositionLike(anyString())).thenReturn(Collections.emptyList());

        assertThrows(NumberFormatException.class, ()->employeeRepository.findEmployeeByPositionLike(anyString()));
        verify(employeeRepository, times(0)).findEmployeeByPosition_Id(anyInt());
        verify(employeeRepository, times(1)).findEmployeeByPositionLike(anyString());
    }



    @Test
    public void shouldGetReportById_AddId_ThenReturnCorrectReport() throws IOException {
when(reportRepository.findReportById(anyInt())).thenReturn(REPORT);

String actual = employeeServiceImpl.getReportById(anyInt());
String expected = "testtest";
assertThat(actual).isEqualTo(expected);

        verify(reportRepository, times(1)).findReportById(anyInt());

    }

//    public String getReportById(Integer id) throws IOException {
//        logger.error("There is no employee with id = " + id);
//        return String.join("", Files.readAllLines
//        (Path.of
//        (reportRepository.findReportById(id).getFilePath())));
//    }


    @Test
    public void shouldAddEmployees_AddListEmployeeDTO_ThenSaveAllToList() {
        when(employeeRepository.saveAll(NEW_EMPLOYEES)).thenReturn(any());
        employeeServiceImpl.addEmployees(EMPLOYEES_DTO);

        verify(employeeRepository, times(1)).saveAll(NEW_EMPLOYEES);

    }
//    public void
//    addEmployees(List<EmployeeDTO> employeeDTO) {
//        logger.info("Was invoked method for add Employees to list " + employeeDTO);
//        employeeRepository.saveAll(employeeDTO
//                .stream()
//                .map(x->x.toEmployee())
//                .toList());
//    }

    @Test
    public void shouldFindAllEmployeesWithHighestSalary_ThenReturnListOfEmployeeWithHighestSalary() {
        when(employeeRepository.findAllEmployeesWithHighestSalary()).thenReturn(NEW_EMPLOYEES);

//        assertIterableEquals(NEW_EMPLOYEES,employeeServiceImpl.findAllEmployeesWithHighestSalary());
        List<Employee> actual = employeeServiceImpl.findAllEmployeesWithHighestSalary();
        List<Employee> expected = NEW_EMPLOYEES;

        assertThat(actual).isEqualTo(expected);

        verify(employeeRepository, times(1)).findAllEmployeesWithHighestSalary();
    }

//    public List<Employee> findAllEmployeesWithHighestSalary() {
//        logger.info("Was invoked method for findAllEmployeesWithHighestSalary  ");
//        return employeeRepository.findAllEmployeesWithHighestSalary();
//    }

    @Test
    public void shouldGetFullInfoBiId_ThenReturnListWithFullInfoOfEmployee() {
        List<EmployeeView> employeeView =List.of(Mockito.mock(EmployeeView.class));



        when(employeeRepository.getFullInfoByIdInProection(anyInt())).thenReturn(employeeView);

        List<EmployeeView> actual = employeeServiceImpl.getFullInfo(anyInt());
        List<EmployeeView> expected = employeeView;

        assertThat(actual).isEqualTo(expected);

        verify(employeeRepository, times(1)).getFullInfoByIdInProection(anyInt());
    }



//    @Override
//    public List<EmployeeView> getFullInfo(int id) {
//        logger.info("Was invoked method for get employee FullInfo By id " + id);
//        return employeeRepository.getFullInfoByIdInProection(id);
//    }



//Page<Employee> page = new PageImpl<>(NEW_EMPLOYEES);     в тестовых данных
//    @Override
//    public List<Employee> getEmployeeWithPaging(int pageIndex, int eployeesInPage) {
//        logger.info("Was invoked method for getEmployeeWithPaging By pageIndex and  eployeesInPage" + pageIndex + eployeesInPage);
//        Page<Employee> page = employeeRepository.findAll(PageRequest.of(pageIndex, eployeesInPage));
//        return page.stream()
//                .toList();
//    }
//
//    @Override
//    public List<Employee> getAllEmployees() {
//        logger.debug("Was invoked method for getAllEmployees ");
//        return employeeRepository.findAllEmployees();
//    }
//
//
//
//    @Override
//    public void addEmployee(EmployeeDTO employeeDTO) {
//        logger.info("Was invoked method for add one Employee ");
//        Employee employee = employeeDTO.toEmployee();
//        employeeRepository.save(employee);
//    }
//
//    @Override
//    @SneakyThrows
//    public void updateEmployee(EmployeeDTO employeeDTO) {
//        logger.info("Was invoked method for updateEmployee"  + employeeDTO);
//        employeeRepository.save(employeeDTO.toEmployee());
//    }
//    @Override
//    @SneakyThrows
//    public Employee findEmployee(int id) {
//        logger.info("Was invoked method for findEmployee by id"  + id);
//        return employeeRepository.findById(id);
//    }
//
//    @Override
//    @SneakyThrows
//    public void deleteEmployee(int id) {
//        logger.info("Was invoked method for delete Employee by id"  + id);
//        employeeRepository.deleteById(id);
//    }
//    @Override
//    public List<EmployeeDTO> findAllEmployeesHigherThanSalary(int testSalary) {
//        logger.info("Was invoked method for find All Employees церш Higher Salary Than testSalary"  + testSalary);
//        List<Employee> employees = employeeRepository.findAllBySalaryGreaterThan(testSalary);
//        List<EmployeeDTO> employeeDTO = new ArrayList<>();
//        for (Employee employee : employees) {
//            EmployeeDTO employeeDTO1 = EmployeeDTO.fromEmployee(employee);
//            employeeDTO.add(employeeDTO1);
//        }
//        return employeeDTO;
//    }






//    public List<Employee> findByPosition(String searchPosition) {
//        logger.info("Was invoked method for find employee By Position " + searchPosition);
//        if (searchPosition.isBlank()) {
//            return employeeRepository.findAllEmployees();
//        }else
//            try {
//                return employeeRepository.findEmployeeByPosition_Id(Integer.parseInt(searchPosition));
//
//            } catch (NumberFormatException exception) {
//                return employeeRepository.findEmployeeByPositionLike(searchPosition);
//            }
//    }











}
