package ru.skypro.lessons.springboot.test.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skypro.lessons.springboot.test.model.Employee;

import java.util.List;


public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findById(int id);

    List<Employee> findAllBySalaryGreaterThan(int testSalary);

    @Query(value = "SELECT * FROM employee e LEFT JOIN position p ON ", nativeQuery = true)
    Employee findEmployeeByNameLikeThis(String name);
}
