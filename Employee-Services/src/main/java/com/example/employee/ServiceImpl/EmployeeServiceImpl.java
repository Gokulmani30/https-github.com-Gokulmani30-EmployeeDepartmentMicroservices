package com.example.employee.ServiceImpl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DTO.EmployeeRequest;
import com.example.DTO.EmployeeResponse;
import com.example.employee.Entity.Employee;
import com.example.employee.Exception.ResourceNotFoundException;
import com.example.employee.Repository.EmployeeRepository;
import com.example.employee.Service.DepartmentClient;
import com.example.employee.Service.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;
    private final DepartmentClient depClient;

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository repo,
                              DepartmentClient depClient) {
        this.repo = repo;
        this.depClient = depClient;
    }

    // ENTITY TO RESPONSE DTO
    private EmployeeResponse mapToResponse(Employee employee) {

        EmployeeResponse dto = new EmployeeResponse();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartmentName(employee.getDepartmentName());
        dto.setSalary(employee.getSalary());

        return dto;
    }

    // REQUEST DTO TO ENTITY
    private Employee mapToEntity(EmployeeRequest dto) {

        Employee employee = new Employee();

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartmentName(dto.getDepartmentName());
        employee.setSalary(dto.getSalary());

        return employee;
    }

    // CREATE EMPLOYEE
    @Override
    public EmployeeResponse create(EmployeeRequest dto) {

        logger.info("Creating new employee");

        validateDepartment(dto.getDepartmentName());

        if (repo.existsByEmailIgnoreCase(dto.getEmail())) {
            logger.warn("Duplicate employee email detected");

            throw new IllegalArgumentException(
                    "Email already exists: " + dto.getEmail());
        }

        Employee employee = mapToEntity(dto);

        Employee savedEmployee = repo.save(employee);

        logger.info("Employee created successfully. ID: {}",
                savedEmployee.getId());

        return mapToResponse(savedEmployee);
    }

    // GET ALL EMPLOYEES WITH PAGINATION AND SORTING
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getAll(Pageable pageable) {

        logger.info("Fetching employees. Page: {}, Size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        return repo.findAll(pageable)
                .map(this::mapToResponse);
    }

    // GET EMPLOYEE BY ID
    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getById(Long id) {

        logger.info("Fetching employee with ID: {}", id);

        Employee employee = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee not found. ID: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with id: " + id);
                });

        return mapToResponse(employee);
    }

    // UPDATE EMPLOYEE
    @Override
    public EmployeeResponse update(Long id, EmployeeRequest dto) {

        logger.info("Updating employee. ID: {}", id);

        Employee existingEmployee = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee not found for update. ID: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with id: " + id);
                });

        validateDepartment(dto.getDepartmentName());

        boolean emailUsedByAnotherEmployee =
                repo.existsByEmailIgnoreCase(dto.getEmail())
                && (existingEmployee.getEmail() == null
                || !existingEmployee.getEmail()
                        .equalsIgnoreCase(dto.getEmail()));

        if (emailUsedByAnotherEmployee) {
            logger.warn("Duplicate email detected during employee update");

            throw new IllegalArgumentException(
                    "Email already exists: " + dto.getEmail());
        }

        existingEmployee.setName(dto.getName());
        existingEmployee.setEmail(dto.getEmail());
        existingEmployee.setSalary(dto.getSalary());
        existingEmployee.setDepartmentName(dto.getDepartmentName());

        Employee updatedEmployee = repo.save(existingEmployee);

        logger.info("Employee updated successfully. ID: {}",
                updatedEmployee.getId());

        return mapToResponse(updatedEmployee);
    }

    // DELETE EMPLOYEE
    @Override
    public void delete(Long id) {

        logger.info("Deleting employee. ID: {}", id);

        Employee employee = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee not found for deletion. ID: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with id: " + id);
                });

        repo.delete(employee);

        logger.info("Employee deleted successfully. ID: {}", id);
    }

    // VALIDATE DEPARTMENT
    private void validateDepartment(String departmentName) {

        if (departmentName == null || departmentName.isBlank()) {
            throw new IllegalArgumentException(
                    "Department name is required");
        }

        if (!depClient.existsByName(departmentName)) {
            logger.warn("Department not found: {}", departmentName);

            throw new IllegalArgumentException(
                    "Department not found: " + departmentName);
        }
    }

    // SEARCH EMPLOYEES
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> searchEmployees(
            String keyword, Pageable pageable) {

        logger.info("Searching employees with keyword: {}", keyword);

        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException(
                    "Search keyword is required");
        }

        return repo.searchEmployees(keyword.trim(), pageable)
                .map(this::mapToResponse);
    }

    // FILTER EMPLOYEES
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> filterEmployees(
            String departmentName,
            BigDecimal minSalary,
            BigDecimal maxSalary,
            Pageable pageable) {

        logger.info("Filtering employees. Department: {}", departmentName);

        if (departmentName != null && departmentName.isBlank()) {
            departmentName = null;
        }

        if (minSalary != null &&
                minSalary.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Minimum salary cannot be negative");
        }

        if (maxSalary != null &&
                maxSalary.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Maximum salary cannot be negative");
        }

        if (minSalary != null && maxSalary != null
                && minSalary.compareTo(maxSalary) > 0) {
            throw new IllegalArgumentException(
                    "Minimum salary cannot exceed maximum salary");
        }

        return repo.filterEmployees(
                departmentName,
                minSalary,
                maxSalary,
                pageable
        ).map(this::mapToResponse);
    }
}
