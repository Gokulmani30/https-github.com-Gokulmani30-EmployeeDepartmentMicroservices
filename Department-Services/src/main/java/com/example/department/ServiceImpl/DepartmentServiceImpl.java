
package com.example.department.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DTO.DepartmentRequest;
import com.example.DTO.DepartmentResponse;
import com.example.department.Entity.Department;
import com.example.department.Exception.ResourceNotFoundException;
import com.example.department.Repository.DepartmentRepository;
import com.example.department.Service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repo;

    private static final Logger logger =
            LoggerFactory.getLogger(DepartmentServiceImpl.class);

    public DepartmentServiceImpl(DepartmentRepository repo) {
        this.repo = repo;
    }

    // ENTITY TO RESPONSE DTO
    private DepartmentResponse mapToResponse(Department department) {

        DepartmentResponse dto = new DepartmentResponse();

        dto.setId(department.getId());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setDescription(department.getDescription());

        return dto;
    }

    // REQUEST DTO TO ENTITY
    private Department mapToEntity(DepartmentRequest dto) {

        Department department = new Department();

        department.setDepartmentName(dto.getDepartmentName());
        department.setDescription(dto.getDescription());

        return department;
    }

    // CREATE DEPARTMENT
    @Override
    public DepartmentResponse create(DepartmentRequest dto) {

        logger.info("Creating new department");

        validateDepartmentName(dto.getDepartmentName());

        boolean departmentExists =
                repo.existsByDepartmentNameIgnoreCase(
                        dto.getDepartmentName());

        if (departmentExists) {

            logger.warn("Department already exists: {}",
                    dto.getDepartmentName());

            throw new IllegalArgumentException(
                    "Department already exists: "
                    + dto.getDepartmentName());
        }

        Department department = mapToEntity(dto);

        Department savedDepartment = repo.save(department);

        logger.info("Department created successfully. ID: {}",
                savedDepartment.getId());

        return mapToResponse(savedDepartment);
    }

    // GET ALL DEPARTMENTS WITH PAGINATION AND SORTING
    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentResponse> getAll(Pageable pageable) {

        logger.info("Fetching departments. Page: {}, Size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        return repo.findAll(pageable)
                .map(this::mapToResponse);
    }

    // GET DEPARTMENT BY ID
    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getById(Long id) {

        logger.info("Fetching department with ID: {}", id);

        Department department = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Department not found. ID: {}", id);

                    return new ResourceNotFoundException(
                            "Department not found with id: " + id);
                });

        return mapToResponse(department);
    }

    // GET DEPARTMENT BY NAME
    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getByName(String departmentName) {

        logger.info("Fetching department by name: {}", departmentName);

        Department department =
                repo.findByDepartmentNameIgnoreCase(departmentName)
                .orElseThrow(() -> {
                    logger.warn("Department not found: {}", departmentName);

                    return new ResourceNotFoundException(
                            "Department not found: " + departmentName);
                });

        return mapToResponse(department);
    }

    // UPDATE DEPARTMENT
    @Override
    public DepartmentResponse update(Long id, DepartmentRequest dto) {

        logger.info("Updating department. ID: {}", id);

        Department existingDepartment = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Department not found for update. ID: {}", id);

                    return new ResourceNotFoundException(
                            "Department not found with id: " + id);
                });

        validateDepartmentName(dto.getDepartmentName());

        boolean departmentExists =
                repo.existsByDepartmentNameIgnoreCase(
                        dto.getDepartmentName());

        if (departmentExists &&
                !existingDepartment.getDepartmentName()
                        .equalsIgnoreCase(dto.getDepartmentName())) {

            logger.warn("Department name already exists: {}",
                    dto.getDepartmentName());

            throw new IllegalArgumentException(
                    "Department already exists: "
                    + dto.getDepartmentName());
        }

        existingDepartment.setDepartmentName(dto.getDepartmentName());
        existingDepartment.setDescription(dto.getDescription());

        Department updatedDepartment = repo.save(existingDepartment);

        logger.info("Department updated successfully. ID: {}",
                updatedDepartment.getId());

        return mapToResponse(updatedDepartment);
    }

    // DELETE DEPARTMENT
    @Override
    public void delete(Long id) {

        logger.info("Deleting department. ID: {}", id);

        Department department = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Department not found for deletion. ID: {}", id);

                    return new ResourceNotFoundException(
                            "Department not found with id: " + id);
                });

        repo.delete(department);

        logger.info("Department deleted successfully. ID: {}", id);
    }

    // VALIDATE DEPARTMENT NAME
    private void validateDepartmentName(String departmentName) {

        if (departmentName == null || departmentName.isBlank()) {

            logger.warn("Department name validation failed");

            throw new IllegalArgumentException(
                    "Department name is required");
        }
    }

    // CHECK DEPARTMENT EXISTS BY NAME
    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String departmentName) {

        logger.info("Checking department existence: {}", departmentName);

        if (departmentName == null || departmentName.isBlank()) {
            return false;
        }

        return repo.existsByDepartmentNameIgnoreCase(departmentName);
    }

    // SEARCH DEPARTMENTS
    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentResponse> searchDepartments(
            String keyword, Pageable pageable) {

        logger.info("Searching departments with keyword: {}", keyword);

        if (keyword == null || keyword.isBlank()) {

            throw new IllegalArgumentException(
                    "Search keyword is required");
        }

        return repo.searchDepartments(keyword.trim(), pageable)
                .map(this::mapToResponse);
    }

    // FILTER DEPARTMENTS
    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentResponse> filterDepartments(
            String departmentName,
            String description,
            Pageable pageable) {

        logger.info("Filtering departments. Department name: {}",
                departmentName);

        if (departmentName != null && departmentName.isBlank()) {
            departmentName = null;
        }

        if (description != null && description.isBlank()) {
            description = null;
        }

        return repo.filterDepartments(
                departmentName,
                description,
                pageable
        ).map(this::mapToResponse);
    }
}
