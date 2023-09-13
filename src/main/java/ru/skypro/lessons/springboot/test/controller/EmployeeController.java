package ru.skypro.lessons.springboot.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.service.EmployeeService;
import ru.skypro.lessons.springboot.test.service.EmployeeView;

import java.io.IOException;
import java.util.List;




@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    @PostMapping (value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        employeeService.upload(file);
    }
    @PostMapping (value = "/report")
    public Integer report() throws JsonProcessingException {
        return employeeService.report();
    }
    @GetMapping("/findReportById/{id}")
    public ResponseEntity<Resource> getReportById(@PathVariable Integer id) throws IOException {
        String fileName = "report.json";
        String json = employeeService.getReportById(id);
        Resource resource = new ByteArrayResource(json.getBytes());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }

    @GetMapping("/withHighestSalary")
    List<Employee> findAllEmployeesWithHighestSalary(){
        List<Employee> tempList = employeeService.findAllEmployeesWithHighestSalary();
        System.out.println(tempList);
        return tempList;
    }
    @GetMapping("/findByPosition")
    List<Employee> findByPosition(@RequestParam ("searchPosition") String searchPosition){
        return employeeService.findByPosition(searchPosition);
    }
    @GetMapping("/{id}/fullInfo")
    List<EmployeeView> getFullInfo(@PathVariable ("id") int id){
        return employeeService.getFullInfo(id);
    }
    @GetMapping("/findByPage")
    List<Employee> getEmployeeWithPaging(@RequestParam (value="pageIndex", defaultValue = "0") @Min(0) int pageIndex,
                                         @RequestParam (value="unitPerPage", defaultValue = "10") @Min(1) @Max(10)
                                         int eployeesInPage )
    {return employeeService.getEmployeeWithPaging(pageIndex, eployeesInPage);}



    //Методы со старых домашек:
    @PostMapping
    void addEmployee(@RequestBody @Valid EmployeeDTO employeeDTO){

        employeeService.addEmployee(employeeDTO);
    }
    @PutMapping
    void updateEmployee (EmployeeDTO employeeDTO){
    employeeService.updateEmployee(employeeDTO);
    }
    @GetMapping("/{id}")
    Employee findEmployee(@PathVariable ("id") int id){
        return employeeService.findEmployee(id);
    }
    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable ("id") int id){
    employeeService.deleteEmployee(id);
    }

    @GetMapping("/findAllEmployees")
    List<Employee> findAllEmployees(){
        return employeeService.getAllEmployees();
    }
    @GetMapping
    List<EmployeeDTO> findAllEmployeesHigherThanSalary(@RequestParam ("testSalary") int testSalary){
    return employeeService.findAllEmployeesHigherThanSalary(testSalary);
    }






//    @GetMapping("/salary/sum")
//    public double getSumOfSalary() {
//       return employeeService.getSumOfSalary();
//    }
//    @GetMapping("/salary/min")
//    public int getMinSalary() {return employeeService.getMinSalary();}
//    @GetMapping("/salary/max")
//    public int getMaxSalary() {
//        return employeeService.getMaxSalary();
//    }
//    @GetMapping("/high-salary")
//    public String getHighSalary() {
//        return employeeService.getHighSalary();
//    }
//    @GetMapping("/salary/averageSalary")
//    public int getAverageSalary() {return employeeService.getAverageSalary();}
}
