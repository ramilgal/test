package ru.skypro.lessons.springboot.test.service;

import lombok.ToString;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.test.model.Employee;
import ru.skypro.lessons.springboot.test.repository.EmployeeRepositoryImpl;

@ToString
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepositoryImpl employeeRepository;

    public EmployeeServiceImpl(EmployeeRepositoryImpl employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public int getSumOfSalary() {
    int sum = 0;
        for (Employee employees: employeeRepository.getEmployeeList()
             ) {sum = sum+employees.getSalary();
        }
        System.out.println("Сумма всех зарплат: " + sum);
        return sum;
    }

    @Override
    public String getMinSalary() {
        int salaryMinimum = Integer.MAX_VALUE;
        String person = null;
        for (Employee employees : employeeRepository.getEmployeeList()) {
            if (employees.getSalary() < salaryMinimum) {
            salaryMinimum = employees.getSalary();
            person=employees.getName();
            }
        }
        return person+" "+salaryMinimum;
    }

    @Override
    public String getMaxSalary() {
        int salaryMaximum = 0;
        String person = null;
        for (Employee employees : employeeRepository.getEmployeeList()) {
            if (employees.getSalary() > salaryMaximum) {
                salaryMaximum = employees.getSalary();
                person = employees.getName();
            }
        }
        return person+" "+salaryMaximum;
    }

    @Override
    public String getHighSalary() {
        int sum = 0;
        int averageSalary;
        int salaryMaximum = 0;
        String person = null;
        for (Employee employees : employeeRepository.getEmployeeList()) {
            sum = sum + employees.getSalary();
        }
        averageSalary = sum / employeeRepository.getEmployeeList().size();
        for (Employee employees : employeeRepository.getEmployeeList()) {
            if (employees.getSalary() > averageSalary) {
                person = employees.getName();
                salaryMaximum = employees.getSalary();
            }
        }
        return person+" "+salaryMaximum;
    }
}
