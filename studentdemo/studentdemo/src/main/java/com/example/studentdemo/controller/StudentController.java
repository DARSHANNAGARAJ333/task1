//package com.example.studentdemo.controller;
//
//import com.example.studentdemo.model.Student;
//import com.example.studentdemo.repository.StudentRepository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/students")
//public class StudentController {
//
//    private final StudentRepository repository;
//
//    public StudentController(StudentRepository repository) {
//        this.repository = repository;
//    }
//
//    @GetMapping
//    public List<Student> getAll() {
//        return repository.findAll();
//    }
//
//    @PostMapping
//    public Student create(@RequestBody Student student) {
//        return repository.save(student);
//    }
//}
package com.example.studentdemo.controller;

import com.example.studentdemo.model.Student;
import com.example.studentdemo.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Student> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return repository.save(student);
    }
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID " + id));
    }

    // Update a student by ID
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return repository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setAge(updatedStudent.getAge());
                    student.setGrade(updatedStudent.getGrade()); // new field
                    student.setAddress(updatedStudent.getAddress()); // new field
                    return repository.save(student);
                })
                .orElseGet(() -> {
                    updatedStudent.setId(id);
                    return repository.save(updatedStudent);
                });
    }

    // Delete a student by ID
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "Student with ID " + id + " has been deleted.";
    }
}

