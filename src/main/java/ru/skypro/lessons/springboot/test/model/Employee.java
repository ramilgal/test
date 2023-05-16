package ru.skypro.lessons.springboot.test.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Employee {
    private String name;
    private int salary;
}
