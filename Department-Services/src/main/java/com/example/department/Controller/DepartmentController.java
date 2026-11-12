
package com.example.department.Controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.DTO.DepartmentRequest;
import com.example.DTO.DepartmentResponse;
import com.example.department.Service.DepartmentService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/departments")
@Tag(
    name = "Department Management",
    description = "APIs for managing departments"
)
public class DepartmentController {

    private final DepartmentService serv;

    public DepartmentController(DepartmentService serv) {
        this.serv = serv;
    }

    // CREATE DEPARTMENT
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create Department",
        description = "Create a new department"
    )
    public DepartmentResponse create(
            @Valid @RequestBody DepartmentRequest dto) {

        return serv.create(dto);
    }

    // GET ALL DEPARTMENTS
    @GetMapping
    @Operation(
        summary = "Get All Departments",
        description = "Get all departments with pagination and sorting"
    )
    public Page<DepartmentResponse> getAll(
            @PageableDefault(
                page = 0,
                size = 5,
                sort = "id",
                direction = Sort.Direction.ASC
            ) Pageable pageable) {

        return serv.getAll(pageable);
    }

    // GET DEPARTMENT BY ID
    @GetMapping("/{id}")
    @Operation(
        summary = "Get Department By ID",
        description = "Retrieve department details using department ID"
    )
    public DepartmentResponse getById(
            @Parameter(description = "Department ID")
            @PathVariable Long id) {

        return serv.getById(id);
    }

    // GET DEPARTMENT BY NAME
    @GetMapping("/name/{departmentName}")
    @Operation(
        summary = "Get Department By Name",
        description = "Find department using department name"
    )
    public DepartmentResponse getByName(
            @PathVariable String departmentName) {

        return serv.getByName(departmentName);
    }

    // UPDATE DEPARTMENT
    @PutMapping("/{id}")
    @Operation(
        summary = "Update Department",
        description = "Update an existing department"
    )
    public DepartmentResponse update(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest dto) {

        return serv.update(id, dto);
    }

    // DELETE DEPARTMENT
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete Department",
        description = "Delete department using department ID"
    )
    public void delete(@PathVariable Long id) {

        serv.delete(id);
    }

    // CHECK DEPARTMENT EXISTS
    @GetMapping("/exists/{departmentName}")
    @Operation(
        summary = "Check Department Exists",
        description = "Check whether a department exists by name"
    )
    public boolean existsByName(
            @PathVariable String departmentName) {

        return serv.existsByName(departmentName);
    }

    // SEARCH DEPARTMENTS
    @GetMapping("/search")
    @Operation(
        summary = "Search Departments",
        description = "Search departments by name or description"
    )
    public Page<DepartmentResponse> searchDepartments(
            @RequestParam String keyword,
            Pageable pageable) {

        return serv.searchDepartments(keyword, pageable);
    }

    // FILTER DEPARTMENTS
    @GetMapping("/filter")
    @Operation(
        summary = "Filter Departments",
        description = "Filter departments by name and description"
    )
    public Page<DepartmentResponse> filterDepartments(
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String description,
            Pageable pageable) {

        return serv.filterDepartments(
                departmentName,
                description,
                pageable
        );
    }
}
