package ru.skypro.lessons.springboot.test.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.skypro.lessons.springboot.test.dto.ReportDTO;
import ru.skypro.lessons.springboot.test.model.Report;

import java.util.List;


public interface ReportRepository extends CrudRepository<Report, Integer> {


    @Query("SELECT new ru.skypro.lessons.springboot.test.dto.ReportDTO (p.position, count(e), max(e.salary), min(e.salary), avg(e.salary)) FROM Employee e LEFT JOIN  Position p ON e.position=p group by p.position")
    List<ReportDTO> getReport();

    @Query("SELECT r FROM Report r where r.id= :searchId")
    String findReportById(@Param("searchId") Integer searchId);




}
