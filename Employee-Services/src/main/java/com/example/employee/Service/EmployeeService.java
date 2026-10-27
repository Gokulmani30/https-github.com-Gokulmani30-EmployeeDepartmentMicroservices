package com.example.employee.Service;

import java.util.List;

import com.example.employee.Entity.Employee;

public interface EmployeeService {

	Employee create(Employee employee);
	
	List<Employee> getAll();
	
	Employee getById(Long id);
	
	Employee update(Long id, Employee employee);
	
	void delete(Long id);
	
	
}
