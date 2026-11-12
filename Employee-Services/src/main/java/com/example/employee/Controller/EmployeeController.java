
package com.example.employee.Controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.DTO.EmployeeRequest;
import com.example.DTO.EmployeeResponse;
import com.example.employee.Service.EmployeeService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/employees")
@Tag(
    name = "Employee Management",
    description = "APIs for managing employees"
)
public class EmployeeController {

    private final EmployeeService serv;

    public EmployeeController(EmployeeService serv) {
        this.serv = serv;
    }

    // CREATE EMPLOYEE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create Employee",
        description = "Create a new employee and validate department"
    )
    public EmployeeResponse create(
            @Valid @RequestBody EmployeeRequest dto) {

        return serv.create(dto);
    }

    // GET ALL EMPLOYEES
    @GetMapping
    @Operation(
        summary = "Get All Employees",
        description = "Get employees with pagination and sorting"
    )
    public Page<EmployeeResponse> getAll(
            @PageableDefault(
                page = 0,
                size = 5,
                sort = "id",
                direction = Sort.Direction.ASC
            ) Pageable pageable) {

        return serv.getAll(pageable);
    }

    // GET EMPLOYEE BY ID
    @GetMapping("/{id}")
    @Operation(
        summary = "Get Employee By ID",
        description = "Retrieve employee details using employee ID"
    )
    public EmployeeResponse getById(
            @Parameter(description = "Employee ID")
            @PathVariable Long id) {

        return serv.getById(id);
    }

    // UPDATE EMPLOYEE
    @PutMapping("/{id}")
    @Operation(
        summary = "Update Employee",
        description = "Update employee details using employee ID"
    )
    public EmployeeResponse update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest dto) {

        return serv.update(id, dto);
    }

    // DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete Employee",
        description = "Delete employee using employee ID"
    )
    public void delete(@PathVariable Long id) {

        serv.delete(id);
    }

    // SEARCH EMPLOYEES
    @GetMapping("/search")
    @Operation(
        summary = "Search Employees",
        description = "Search employees by name, email or department"
    )
    public Page<EmployeeResponse> searchEmployees(
            @RequestParam String keyword,
            Pageable pageable) {

        return serv.searchEmployees(keyword, pageable);
    }

    // FILTER EMPLOYEES
    @GetMapping("/filter")
    @Operation(
        summary = "Filter Employees",
        description = "Filter employees by department and salary range"
    )
    public Page<EmployeeResponse> filterEmployees(
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) BigDecimal minSalary,
            @RequestParam(required = false) BigDecimal maxSalary,
            Pageable pageable) {

        return serv.filterEmployees(
                departmentName,
                minSalary,
                maxSalary,
                pageable
        );
    }
}
