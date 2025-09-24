package com.example.employee_management.repository;



import com.example.employee_management.model.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JDBC repository for Employee CRUD operations
 */

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employees",
                new BeanPropertyRowMapper<>(Employee.class));
    }

    public Optional<Employee> findById(Long id) {
        List<Employee> result = jdbcTemplate.query(
                "SELECT * FROM employees WHERE id = ?",
                new BeanPropertyRowMapper<>(Employee.class),
                id
        );
        return result.stream().findFirst();
    }

    public int save(Employee employee) {
        return jdbcTemplate.update(
                "INSERT INTO employees (name, department, position, salary) VALUES (?, ?, ?, ?)",
                employee.getName(), employee.getDepartment(),
                employee.getPosition(), employee.getSalary()
        );
    }

    public int update(Employee employee) {
        return jdbcTemplate.update(
                "UPDATE employees SET name=?, department=?, position=?, salary=? WHERE id=?",
                employee.getName(), employee.getDepartment(), employee.getPosition(),
                employee.getSalary(), employee.getId()
        );
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM employees WHERE id=?", id);
    }
}
