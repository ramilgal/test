package ru.skypro.lessons.springboot.test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table (name="report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @Lob
//    (Если его включить, выходит ошибка: "Большие объекты не могут использоваться в режиме авто-подтверждения (auto-commit)."
    @Column(name = "file_path", nullable = false, length = 50)
//            (columnDefinition = "clob")
    private String filePath;

    public Report(String filePath) {
        this.filePath = filePath;
    }
}
