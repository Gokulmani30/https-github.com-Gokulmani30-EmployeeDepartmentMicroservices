package com.example.employee.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.DTO.EmployeeRequest;
import com.example.DTO.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse create(EmployeeRequest dto);

    Page<EmployeeResponse> getAll(Pageable pageable);

    EmployeeResponse getById(Long id);

    EmployeeResponse update(Long id, EmployeeRequest dto);

    void delete(Long id);
    

Page<EmployeeResponse> searchEmployees(
        String keyword, Pageable pageable);

Page<EmployeeResponse> filterEmployees(
        String departmentName,
        BigDecimal minSalary,
        BigDecimal maxSalary,
        Pageable pageable);

}
