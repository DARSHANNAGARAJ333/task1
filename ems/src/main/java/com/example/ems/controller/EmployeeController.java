package com.example.ems.controller;

import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service){ this.service = service; }

    @GetMapping
    public List<Employee> list(){ return service.list(); }

    @PostMapping
    public Employee create(@RequestBody Employee e){ return service.create(e); }

    @GetMapping("/{id}")
    public Employee get(@PathVariable Long id){ return service.get(id); }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee e){ return service.update(id,e); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}

