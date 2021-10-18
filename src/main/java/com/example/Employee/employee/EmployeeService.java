package com.example.Employee.employee;

import com.example.Employee.employee.exception.BadRequestException;
import com.example.Employee.employee.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployee() {
        return employeeRepository.findAll();
    }

    public void addNewEmployee(Employee employee) {
        System.out.println(employee);
        Boolean existsEmail = employeeRepository
                .selectExistsEmail(employee.getEmail());
        if (existsEmail) {
            throw new BadRequestException(
                    "Email " + employee.getEmail() + " taken");
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(long id) {
        if(!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee with EmployeeId: " + id + " does not exist");
        } else {
            employeeRepository.deleteById(id);
        }
    }

}
