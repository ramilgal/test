package ru.skypro.lessons.springboot.test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @Lob
    @Column(columnDefinition = "clob")
    private String report;

    public Report(String report) {
        this.report = report;
    }
}
