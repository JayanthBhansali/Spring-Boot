package com.example.Employee.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EmployeeConfig {

    @Bean
    CommandLineRunner commandLineRunner(EmployeeRepository repository) {
        return args -> {
            Employee jay = new Employee(
                    "Jayanth",
                    21,
                    "jayanth@gmail.com"
            );
            Employee shivam = new Employee(
                    "Shivam",
                    24,
                    "shivam@gmail.com"
            );

            repository.saveAll(
                    List.of(jay, shivam)
            );
        };
    }
}
