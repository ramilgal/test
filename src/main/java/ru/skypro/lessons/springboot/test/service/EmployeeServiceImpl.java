package ru.skypro.lessons.springboot.test.service;

import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Override
    public double getSumOfSalary() {

        return 0;
    }

    @Override
    public String getMinSalary() {
        return "0";
    }

    @Override
    public String getMaxSalary() {
        return "0";
    }

    @Override
    public String getHighSalary() {
        return "0";
    }
}
