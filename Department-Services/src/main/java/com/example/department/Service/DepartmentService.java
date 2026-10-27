package com.example.department.Service;

import java.util.List;

import com.example.department.Entity.Department;

public interface DepartmentService {

	Department create(Department department);

    List<Department> getAll();

    Department getById(Long id);
    
    Department getByName(String name);

    Department update(Long id, Department department);

    void delete(Long id);
}
