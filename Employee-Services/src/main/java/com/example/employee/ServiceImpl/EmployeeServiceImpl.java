
package com.example.employee.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.employee.Entity.Employee;
import com.example.employee.Repository.EmployeeRepository;
import com.example.employee.Service.DepartmentClient;
import com.example.employee.Service.EmployeeService;
import com.example.employee.Exception.ResourceNotFoundException;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;
    private final DepartmentClient depClient;

    public EmployeeServiceImpl(
            EmployeeRepository repo,
            DepartmentClient depClient) {
        this.repo = repo;
        this.depClient = depClient;
    }

    @Override
    public Employee create(Employee employee) {

        validateDepartment(employee.getDepartmentName());

        if (repo.existsByEmailIgnoreCase(employee.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already exists: " + employee.getEmail());
        }

        return repo.save(employee);
    }

    

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));
    }

    @Override
    public Employee update(Long id, Employee employee) {

        Employee existingEmployee = getById(id);

        validateDepartment(employee.getDepartmentName());

        boolean emailUsedByAnotherEmployee =
                repo.existsByEmailIgnoreCase(employee.getEmail())
                && !existingEmployee.getEmail()
                        .equalsIgnoreCase(employee.getEmail());

        if (emailUsedByAnotherEmployee) {
            throw new IllegalArgumentException(
                    "Email already exists: " + employee.getEmail());
        }

        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setDepartmentName(employee.getDepartmentName());

        return repo.save(existingEmployee);
    }

    @Override
    public void delete(Long id) {
        Employee employee = getById(id);
        repo.delete(employee);
    }
    private void validateDepartment(String departmentname) {

        if (departmentname == null || departmentname.isBlank()) {
            throw new IllegalArgumentException(
                    "Department Name is required");
        }
        if (!depClient.existsByName(departmentname)) {
            throw new IllegalArgumentException(
                    "Department not found: " + departmentname);
        }

        // Verify that the department exists using DepartmentClient.
        // Implement this according to your DepartmentClient method.
    }
}
