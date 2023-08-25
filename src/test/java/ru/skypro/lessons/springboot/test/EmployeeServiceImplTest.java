package ru.skypro.lessons.springboot.test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Any;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.dto.PositionDTO;
import ru.skypro.lessons.springboot.test.dto.ReportDTO;
import ru.skypro.lessons.springboot.test.exceptions.IllegalJsonFileException;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.model.Report;
import ru.skypro.lessons.springboot.test.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.test.repository.ReportRepository;
import ru.skypro.lessons.springboot.test.service.EmployeeServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.skypro.lessons.springboot.test.constants.ConstantsOfEmployee.*;

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

    @Test
    public void shouldFindEmployeeByPositionLike_NumberFormatException_AddSearchPosition_ThenReturnCorrectListOfEmployees(){
        when(employeeRepository.findEmployeeByPositionLike(anyString())).thenThrow(NumberFormatException.class);

        assertThrows(NumberFormatException.class, ()->employeeRepository.findEmployeeByPositionLike(anyString()));

        verify(employeeRepository, times(1)).findEmployeeByPositionLike(anyString());
    }



    @Test
    public void shouldGetReportById_AddId_ThenReturnCorrectReport(){


    }

//    public String getReportById(Integer id) throws IOException {
//        logger.error("There is no employee with id = " + id);
//        return String.join("", Files.readAllLines(Path.of(reportRepository.findReportById(id).getFilePath())));
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

        assertIterableEquals(NEW_EMPLOYEES,employeeServiceImpl.findAllEmployeesWithHighestSalary());
//        List<Employee> actual = employeeServiceImpl.findAllEmployeesWithHighestSalary();
//        List<Employee> expected = NEW_EMPLOYEES;
//
//        assertThat(actual).isEqualTo(expected);

        verify(employeeRepository, times(1)).findAllEmployeesWithHighestSalary();
    }

//    public List<Employee> findAllEmployeesWithHighestSalary() {
//        logger.info("Was invoked method for findAllEmployeesWithHighestSalary  ");
//        return employeeRepository.findAllEmployeesWithHighestSalary();
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
