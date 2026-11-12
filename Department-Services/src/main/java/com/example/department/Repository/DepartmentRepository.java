
package com.example.department.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.department.Entity.Department;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentNameIgnoreCase(
            String departmentName);

    boolean existsByDepartmentNameIgnoreCase(
            String departmentName);

    // SEARCH BY NAME OR DESCRIPTION
    @Query("""
        SELECT d FROM Department d
        WHERE LOWER(d.departmentName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(d.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    Page<Department> searchDepartments(
            @Param("keyword") String keyword,
            Pageable pageable);

    // FILTER BY DEPARTMENT NAME AND DESCRIPTION
    @Query("""
        SELECT d FROM Department d
        WHERE (:departmentName IS NULL OR
        LOWER(d.departmentName) = LOWER(:departmentName))
        AND (:description IS NULL OR
        LOWER(d.description) LIKE LOWER(CONCAT('%', :description, '%')))
        """)
    Page<Department> filterDepartments(
            @Param("departmentName") String departmentName,
            @Param("description") String description,
            Pageable pageable);
}
