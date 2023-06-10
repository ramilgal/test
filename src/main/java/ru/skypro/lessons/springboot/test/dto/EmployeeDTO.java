package ru.skypro.lessons.springboot.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.model.Position;

@Data
@AllArgsConstructor
public class EmployeeDTO {

        private Integer id;
        private String name;
        private Integer salary;
        private PositionDTO positionDTO;
        public EmployeeDTO() {
        }

    // Метод для преобразования сущности Employee в объект EmployeeDTO
        public static EmployeeDTO fromEmployee(Employee employee) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setName(employee.getName());
            employeeDTO.setSalary(employee.getSalary());
//            employeeDTO.setPositionDTO(new PositionDTO(employee.getPosition().getId(), employee.getPosition().getName()));//обернуть в toPositionDTO
            employeeDTO.setPositionDTO(PositionDTO.fromPosition(employee.getPosition()));
            return employeeDTO;
        }

        // Метод для преобразования объекта EmployeeDTO в сущность Employee
        public Employee toEmployee() {
            Employee employee = new Employee();
            employee.setId(this.getId());
            employee.setName(this.getName());
            employee.setSalary(this.getSalary());
            employee.setPosition(this.getPositionDTO().toPosition()); //fromPositionDTO
            return employee;
        }
}
