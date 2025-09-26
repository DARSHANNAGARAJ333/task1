package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repo;
    public EmployeeService(EmployeeRepository repo) { this.repo = repo; }

    public Employee create(Employee e) { return repo.save(e); }
    public Employee update(Long id, Employee e) {
        Employee existing = repo.findById(id).orElseThrow();
        existing.setFirstName(e.getFirstName());
        existing.setLastName(e.getLastName());
        existing.setEmail(e.getEmail());
        return repo.save(existing);
    }
    public void delete(Long id) { repo.deleteById(id); }
    public Employee get(Long id) { return repo.findById(id).orElseThrow(); }
    public List<Employee> list() { return repo.findAll(); }
}

