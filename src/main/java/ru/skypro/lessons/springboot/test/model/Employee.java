package ru.skypro.lessons.springboot.test.model;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Data
public class Employee {
    @NotBlank (message = "Введите имя")
    private String name;
    @Positive
    private int salary;
    @NotNull
    private int id;
}
