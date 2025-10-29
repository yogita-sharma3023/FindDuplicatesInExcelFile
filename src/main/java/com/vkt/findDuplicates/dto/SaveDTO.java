package com.vkt.findDuplicates.dto;

import java.util.List;

import com.vkt.findDuplicates.model.Employee;

public class SaveDTO {

	private String flag;
	
	private List<Employee> employee;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}
	
	
}
