
package com.example.employee.Repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.employee.Entity.Employee;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    boolean existsByEmailIgnoreCase(String email);

    // SEARCH BY NAME, EMAIL OR DEPARTMENT
    @Query("""
        SELECT e FROM Employee e
        WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(e.departmentName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    Page<Employee> searchEmployees(
            @Param("keyword") String keyword,
            Pageable pageable);

    // FILTER EMPLOYEES
    @Query("""
        SELECT e FROM Employee e
        WHERE (:departmentName IS NULL OR
        LOWER(e.departmentName) = LOWER(:departmentName))
        AND (:minSalary IS NULL OR e.salary >= :minSalary)
        AND (:maxSalary IS NULL OR e.salary <= :maxSalary)
        """)
    Page<Employee> filterEmployees(
            @Param("departmentName") String departmentName,
            @Param("minSalary") BigDecimal minSalary,
            @Param("maxSalary") BigDecimal maxSalary,
            Pageable pageable);
}
