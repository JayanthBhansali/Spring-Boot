package com.example.Employee.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployee() {
        return employeeService.getEmployee();
    }

    @PostMapping
    public void registerEmployee(@RequestBody Employee employee) {
        employeeService.addNewEmployee(employee);
    }

    @DeleteMapping(path = "{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") long id) {
        employeeService.deleteEmployee(id);
    }

}
