
package com.example.department.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Employee name is required")
    @Size(max = 100, message = "Employee name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String employeeName;

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name cannot exceed 100 characters")
    @Column(nullable = false, unique = true, length = 100)
    private String departmentName;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Column(length = 255)
    private String description;

    public Department() {
    }

    public Department(Long id, String employeeName,
                      String departmentName, String description) {
        this.id = id;
        this.employeeName = employeeName;
        this.departmentName = departmentName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
