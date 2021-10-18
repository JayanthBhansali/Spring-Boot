package com.example.Employee.employee;

import com.example.Employee.employee.exception.BadRequestException;
import com.example.Employee.employee.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    private EmployeeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new EmployeeService(employeeRepository);
    }

    @Test
    void testGetEmployee() {
        // when
        underTest.getEmployee();
        // then
        verify(employeeRepository).findAll();
    }

    @Test
    void testAddNewEmployee() {
        // given
        Employee employee = new Employee(
                "Jay B",
                45,
                "jay@gmail.com"
        );

        // when
        underTest.addNewEmployee(employee);

        // then
        ArgumentCaptor<Employee> employeeArgumentCaptor =
                ArgumentCaptor.forClass(Employee.class);

        verify(employeeRepository)
                .save(employeeArgumentCaptor.capture());

        Employee capturedEmployee = employeeArgumentCaptor.getValue();

        assertThat(capturedEmployee).isEqualTo(employee);
    }

    @Test
    void willThrowErrorWhenEmailIsTaken() {
        // given
        Employee employee = new Employee(
                "Jay B",
                50,
                "jay@gmail.com"
        );

        // when
        given(employeeRepository.selectExistsEmail(anyString()))
                .willReturn(true);

        // then
        assertThatThrownBy(() -> underTest.addNewEmployee(employee))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + employee.getEmail() + " taken");

        verify(employeeRepository, never()).save(any());

    }

    @Test
    void testDeleteEmployee() {

        // given
        long id = 10;
        given(employeeRepository.existsById(id))
                .willReturn(true);

        // when
        underTest.deleteEmployee(id);

        // then
        verify(employeeRepository).deleteById(id);
    }

    @Test
    void willThrowErrorWhenDeleteStudentNotFound() {
        // given
        long id = 10;

        // when
        given(employeeRepository.existsById(id))
                .willReturn(false);

        // then
        assertThatThrownBy(() -> underTest.deleteEmployee(id))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("Employee with EmployeeId: " + id + " does not exist");

        verify(employeeRepository, never()).deleteById(any());
    }
}