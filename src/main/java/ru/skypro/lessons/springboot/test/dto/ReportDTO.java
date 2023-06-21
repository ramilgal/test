package ru.skypro.lessons.springboot.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportDTO {
    private String position;
    private Long countOfEmployees;
    private Integer maxSalary;
    private Integer minSalary;
    private Double averageSalary;

}
