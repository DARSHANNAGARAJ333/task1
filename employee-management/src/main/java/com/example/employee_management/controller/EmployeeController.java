package com.example.employee_management.controller;

import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Employee one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody Employee employee) {
        service.create(employee);
        return ResponseEntity.ok("Employee created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        service.update(id, employee);
        return ResponseEntity.ok("Employee updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Employee deleted");
    }

    @GetMapping("/filter/salary/{min}")
    public List<Employee> filterBySalary(@PathVariable double min) {
        // Convert double to BigDecimal before passing to service
        BigDecimal minSalary = BigDecimal.valueOf(min);
        return service.filterBySalary(minSalary);
    }
}
