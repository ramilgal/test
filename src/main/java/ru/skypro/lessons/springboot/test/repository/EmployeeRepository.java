package ru.skypro.lessons.springboot.test.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.service.EmployeeView;

import java.util.List;


public interface EmployeeRepository extends CrudRepository<Employee, Integer>, PagingAndSortingRepository<Employee, Integer> {

    @Query("SELECT e,p FROM Employee e JOIN FETCH Position p ON e.position = p where e.salary = (Select max(salary) from Employee)")
    List<Employee> findAllEmployeesWithHighestSalary();

    @Query("SELECT e,p FROM Employee e LEFT JOIN Position p ON e.position=p where p.position= :searchPosition")
//    @Query(value = "SELECT * FROM employee e LEFT JOIN position p ON e.position_id=p.id where p.position= :searchPosition", nativeQuery = true)
    List <Employee> findEmployeeByPositionLike(@Param("searchPosition") String searchPosition);
    @Query("SELECT e FROM Employee e where e.id= :searchId")
    List <EmployeeView> getFullInfoByIdInProection(@Param("searchId") int id);


//Старые методы с домашек для тренировки:
    @Query("SELECT e,p FROM Employee e LEFT JOIN Position p ON e.position = p")
    //    @Query(value = "SELECT e.id, e.name,e.salary, e.position_id, p.id, p.position FROM employee e LEFT JOIN position p ON e.position_id=p.id", nativeQuery = true)
    List<Employee> findAllEmployees();

    //    @Query(value = "SELECT * FROM employee e LEFT JOIN position p ON e.position_id=p.id where p.id= :id", nativeQuery = true)
    List <Employee> findEmployeeByPosition_Id(int searchId);


    Employee findById(int id);

    List<Employee> findAllBySalaryGreaterThan(int testSalary);
//    @Query(value = "SELECT * FROM employee e LEFT JOIN position p ON e.position_id=p.id where e.salary = (SELECT MAX(salary) FROM employee)", nativeQuery = true)

    @Query(value = "SELECT * FROM employee e LEFT JOIN position p ON e.position_id=p.id where p.position=:NewName", nativeQuery = true)
    Employee findEmployeeByNameLikeThis(@Param("NewName") String name);






}
