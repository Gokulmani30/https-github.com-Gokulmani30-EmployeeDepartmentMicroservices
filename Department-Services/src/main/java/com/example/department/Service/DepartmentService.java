
package com.example.department.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.DTO.DepartmentRequest;
import com.example.DTO.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse create(DepartmentRequest dto);

    Page<DepartmentResponse> getAll(Pageable pageable);

    DepartmentResponse getById(Long id);

    DepartmentResponse getByName(String departmentName);

    DepartmentResponse update(Long id, DepartmentRequest dto);

    boolean existsByName(String departmentName);


    void delete(Long id);


Page<DepartmentResponse> searchDepartments(
        String keyword, Pageable pageable);

Page<DepartmentResponse> filterDepartments(
        String departmentName,
        String description,
        Pageable pageable);

}
