package com.example.department.Controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.department.Entity.Department;
import com.example.department.Service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(
            @Valid @RequestBody Department department) {
        return service.create(department);
    }

    // READ ALL
    @GetMapping
    public List<Department> getAll() {
        return service.getAll();
    }

    // READ BY ID

    @GetMapping("/name/{name}")
    public Department getByName(@PathVariable String name) {
        return service.getByName(name);
    }
    // UPDATE
    @PutMapping("/{id}")
    public Department update(
            @PathVariable Long id,
            @Valid @RequestBody Department department) {
        return service.update(id, department);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}