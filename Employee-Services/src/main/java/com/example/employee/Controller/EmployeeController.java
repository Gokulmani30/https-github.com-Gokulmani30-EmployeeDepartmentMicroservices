package com.example.employee.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.Entity.Employee;
import com.example.employee.Service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private final EmployeeService serv;
	
	public EmployeeController(EmployeeService serv) {
		this.serv = serv;
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Employee create(@Valid @RequestBody Employee employee) {
		return serv.create(employee);
		
	}
	
	@GetMapping
	public List<Employee> getAll(){
		return serv.getAll();
		
	}
	
	@GetMapping("/{id}")
	public Employee getById(@PathVariable Long id) {
		return serv.getById(id);
	}
	
	@PutMapping("/{id}")
	public Employee update(@PathVariable Long id,
			@RequestBody @Valid Employee employee) {
		
		return serv.update(id, employee);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	
	public void delete(@PathVariable Long id) {
		serv.delete(id);
	}
}
