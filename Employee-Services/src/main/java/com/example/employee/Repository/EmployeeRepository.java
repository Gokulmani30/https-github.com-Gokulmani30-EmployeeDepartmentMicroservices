package com.example.employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.Entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	boolean existsByEmailIgnoreCase(String email);
}
