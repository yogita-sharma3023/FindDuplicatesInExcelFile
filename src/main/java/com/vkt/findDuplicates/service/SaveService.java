package com.vkt.findDuplicates.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vkt.findDuplicates.dto.APIResponse;
import com.vkt.findDuplicates.dto.SaveDTO;
import com.vkt.findDuplicates.model.Employee;
import com.vkt.findDuplicates.repository.EmployeeRepository;

@Service
public class SaveService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public ResponseEntity<?> saveExcel(SaveDTO saveDTO){
		System.out.println("Raw flag received = '" + saveDTO.getFlag() + "'");
		 System.out.println("Employees list size = " + saveDTO.getEmployee().size());
		 if (saveDTO.getFlag().equalsIgnoreCase("Y")) {
			    List<Employee> uniqueList = new ArrayList<>();
			    List<String> duplicateInDb = new ArrayList<>();

			    for (Employee emp : saveDTO.getEmployee()) {
			        boolean exists = employeeRepository.existsByEmail(emp.getEmail())
			                || employeeRepository.existsByPhone(emp.getPhone())
			                || employeeRepository.existsByName(emp.getName());

			        if (exists) {
			            duplicateInDb.add(emp.getName() + " | " + emp.getEmail() + " | " + emp.getPhone());
			        } else {
			            uniqueList.add(emp);
			        }
			    }

			    
			    if (!uniqueList.isEmpty()) {
			        employeeRepository.saveAll(uniqueList);
			    }

			    
			    APIResponse<Object> response = new APIResponse<>(
			            duplicateInDb.isEmpty() ? "SUCCESS" : "PARTIAL_SUCCESS",
			            duplicateInDb.isEmpty() ? HttpStatus.OK.value() : HttpStatus.MULTI_STATUS.value(),
			            "Saved " + uniqueList.size() + " employees. " +
			                    (duplicateInDb.isEmpty() ? "" : duplicateInDb.size() + " duplicates found."),
			            !duplicateInDb.isEmpty() ? duplicateInDb : uniqueList
			    );
			    return ResponseEntity.ok(response);
			}
	    
		System.out.println("Going to save for n flag");
	    employeeRepository.saveAll(saveDTO.getEmployee());
	    APIResponse<Object> response = new APIResponse<>(
	            "SUCCESS",
	            HttpStatus.OK.value(),
	            "Saved " + saveDTO.getEmployee().size() + " employees successfully (without DB check).",
	            saveDTO.getEmployee()
	    );
	    return ResponseEntity.ok(response);
	    
	}
	
}
