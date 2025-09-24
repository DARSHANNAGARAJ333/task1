package com.example.employee_management.service;

import com.example.employee_management.exception.ResourceNotFoundException;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles Employee CRUD and extra filtering logic
 */
@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public List<Employee> getAll() {
        return repo.findAll();
    }

    public Employee getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

    public void create(Employee employee) {
        repo.save(employee);
    }

    public void update(Long id, Employee employee) {
        Employee existing = getById(id); // ensure exists
        employee.setId(existing.getId()); // preserve id
        repo.update(employee);
    }


    public void delete(Long id) {
        repo.deleteById(id);
    }

    // Filter employees with salary >= minSalary
    public List<Employee> filterBySalary(BigDecimal minSalary) {
        return getAll().stream()
                .filter(e -> e.getSalary().compareTo(minSalary) >= 0)
                .collect(Collectors.toList());
    }
}
