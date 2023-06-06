package ru.skypro.lessons.springboot.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skypro.lessons.springboot.test.model.Employee;
@Data
@AllArgsConstructor
public class EmployeeDTO {

        private Integer id;
        private String name;
        private Integer salary;
        public EmployeeDTO() {
        }

    // Метод для преобразования сущности Employee в объект EmployeeDTO
        public static EmployeeDTO fromEmployee(Employee employee) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setName(employee.getName());
            employeeDTO.setSalary(employee.getSalary());
            return employeeDTO;
        }

        // Метод для преобразования объекта EmployeeDTO в сущность Employee
        public Employee toEmployee() {
            Employee employee = new Employee();
            employee.setId(this.getId());
            employee.setName(this.getName());
            employee.setSalary(this.getSalary());
            return employee;
        }
}
