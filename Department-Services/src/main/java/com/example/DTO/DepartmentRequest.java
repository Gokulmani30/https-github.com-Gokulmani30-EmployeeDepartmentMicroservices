package com.example.DTO;

public class DepartmentRequest {

    private String departmentName;
    private String description;
    private String employeeName;
    
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
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

    
}