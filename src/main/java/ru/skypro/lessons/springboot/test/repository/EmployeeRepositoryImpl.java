package ru.skypro.lessons.springboot.test.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.test.model.Employee;

import java.util.ArrayList;
import java.util.List;
@Repository
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final List<Employee> employeeList = new ArrayList<>(List.of(
            new Employee("Ivan", 50000, 1),
            new Employee("Petr", 55000, 2),
            new Employee("Irina", 60000, 3),
            new Employee("Mila", 65000, 4),
            new Employee("Pavel", 70000, 5)));

    @Override
    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    @Override
    @SneakyThrows
    public void deleteEmployee(int id) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId() == id) {
                employeeList.remove(employeeList.get(i));
                return;
            }
        }
        throw new Exception();
    }

    @Override
    @SneakyThrows
    public List<Employee> findEmployee(int id) {
        List<Employee> findedEmployee = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                findedEmployee.add(employee);
            }
        }
        return findedEmployee;
    }

    public void addEmployee(List<Employee> employee) {
        employeeList.addAll(employee);
    }

    public void updateEmployee(Employee employee) {
        for (Employee employeeToUpdate : new ArrayList<>(employeeList)) {
            if (employee.getId() == employeeToUpdate.getId()) {
                employeeList.remove(employeeToUpdate);
                employeeList.add(employee);
            }
        }
    }
}
