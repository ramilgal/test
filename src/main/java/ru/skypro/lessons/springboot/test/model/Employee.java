package ru.skypro.lessons.springboot.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank (message = "Введите имя")
    @Column(nullable = false, length = 50)
    private String name;
    @Positive
    private int salary;
    @JoinColumn (name = "position_id")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Position position;

}
