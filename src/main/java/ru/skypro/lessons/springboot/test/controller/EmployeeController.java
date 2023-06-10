package ru.skypro.lessons.springboot.test.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.service.EmployeeService;
import ru.skypro.lessons.springboot.test.service.EmployeeView;


import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

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






//Эти методы оставил с прошлой домашки:
//    @GetMapping("/salary/sum")
//    public double getSumOfSalary() {
//       return employeeService.getSumOfSalary();
//    }
//    @GetMapping("/salary/min")
//    public String getMinSalary() {
//        return employeeService.getMinSalary();
//    }
//    @GetMapping("/salary/max")
//    public String getMaxSalary() {
//        return employeeService.getMaxSalary();
//    }
//    @GetMapping("/high-salary")
//    public String getHighSalary() {
//        return employeeService.getHighSalary();
//    }
}
