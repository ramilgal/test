package ru.skypro.lessons.springboot.test.constants;

import ru.skypro.lessons.springboot.test.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.test.dto.ReportDTO;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.model.Position;
import ru.skypro.lessons.springboot.test.model.Report;

import java.util.ArrayList;
import java.util.List;

public class ConstantsOfEmployee {
    public static final Position POSITION = new Position(1232, "test");
    public static final Employee EMPLOYEE = new Employee(1212, "test", 1, POSITION);
    public static final List<ReportDTO> NEW_REPORT = new ArrayList<>(){{
        add(new ReportDTO("one", 1212L,50000, 70000, 122222.00));
        add(new ReportDTO("gf", 1212L,50000, 70000, 122222.00));
        add(new ReportDTO("df", 1212L,50000, 70000, 122222.00));
    }};
    public static final Report REPORT = new Report(22, "ds");
    public static final List<Employee> NEW_EMPLOYEES = new ArrayList<>(){{
        add(new Employee(4, "test", 45000, new Position(45, "one")));
        add(new Employee(5, "test", 45000, new Position(45, "one")));
    }};
    public static final List<EmployeeDTO> EMPLOYEES_DTO = new ArrayList<>(){{
        add(EmployeeDTO.fromEmployee(new Employee(4, "test", 45000, new Position(45, "one"))));
        add(EmployeeDTO.fromEmployee(new Employee(5, "test", 45000, new Position(45, "one"))));
    }};

}
