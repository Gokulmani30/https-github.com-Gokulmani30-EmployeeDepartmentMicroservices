
package com.example.department.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.department.Entity.Department;
import com.example.department.Exception.ResourceNotFoundException;
import com.example.department.Repository.DepartmentRepository;
import com.example.department.Service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Override
    public Department create(Department department) {

        boolean exists = repository
                .existsByDepartmentNameIgnoreCase(
                        department.getDepartmentName());

        if (exists) {
            throw new IllegalArgumentException(
                    "Department name already exists: "
                            + department.getDepartmentName());
        }

        return repository.save(department);
    }

    // READ ALL
    @Override
    @Transactional(readOnly = true)
    public List<Department> getAll() {
        return repository.findAll();
    }

    // READ BY ID
    @Override
    @Transactional(readOnly = true)
    public Department getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id));
    }

    // READ BY DEPARTMENT NAME
    @Override
    @Transactional(readOnly = true)
    public Department getByName(String name) {
        return repository.findByDepartmentNameIgnoreCase(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found: " + name));
    }

    // UPDATE
    @Override
    public Department update(Long id, Department input) {

        Department department = getById(id);

        boolean nameUsedByAnotherDepartment =
                repository.existsByDepartmentNameIgnoreCase(
                        input.getDepartmentName())
                && !department.getDepartmentName()
                        .equalsIgnoreCase(input.getDepartmentName());

        if (nameUsedByAnotherDepartment) {
            throw new IllegalArgumentException(
                    "Department name already exists: "
                            + input.getDepartmentName());
        }

        department.setDepartmentName(input.getDepartmentName());
        department.setEmployeeName(input.getEmployeeName());
        department.setDescription(input.getDescription());

        return repository.save(department);
    }

    // DELETE
    @Override
    public void delete(Long id) {
        Department department = getById(id);
        repository.delete(department);
    }
}
