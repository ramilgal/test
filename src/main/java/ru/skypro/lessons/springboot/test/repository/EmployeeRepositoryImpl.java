package ru.skypro.lessons.springboot.test.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.test.model.Employee;

import java.util.List;
@Repository
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRepositoryImpl implements EmployeeRepository{
    private final List<Employee> employeeList = List.of(
            new Employee("Ivan", 50000),
            new Employee("Petr", 55000),
            new Employee("Irina", 60000),
            new Employee("Mila", 65000),
            new Employee("Pavel", 70000));

    @Override
    public List<Employee> getAllEmployees() {
        return employeeList;
    }
}
