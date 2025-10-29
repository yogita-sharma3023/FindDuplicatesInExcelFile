package com.vkt.findDuplicates.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.vkt.findDuplicates.model.Employee;

@Service
public class DuplicateFinder {
public List<Employee> findDuplicates(List<Employee> employees) {

	    
	    Map<String, List<Integer>> emailMap = new HashMap<>();
	   
	    Map<Long, List<Integer>> phoneMap = new HashMap<>();

	    Map<String, List<Integer>> nameMap = new HashMap<>();
	    
	    
	    
	   
	    for (Employee emp : employees) {
	        emailMap.computeIfAbsent(emp.getEmail(), k -> new ArrayList<>()).add(emp.getId());
	        phoneMap.computeIfAbsent(emp.getPhone(), k -> new ArrayList<>()).add(emp.getId());
	        nameMap.computeIfAbsent(emp.getName(), k -> new ArrayList<>()).add(emp.getId());
	        
	    }

	   
	    List<String> duplicateEmails = new ArrayList<>();
	    List<String> duplicatePhones = new ArrayList<>();
        List<String> duplicateNames = new ArrayList<>();
	    
	    for (Entry<String, List<Integer>> entry : emailMap.entrySet()) {
	        List<Integer> ids = entry.getValue();
	        if (ids.size() > 1) {
	            duplicateEmails.add(entry.getKey() + " appears " + ids.size() + " times in IDs " + ids);
	        }
	    }

	    
	    for (Entry<Long, List<Integer>> entry : phoneMap.entrySet()) {
	        List<Integer> ids = entry.getValue();
	        if (ids.size() > 1) {
	            duplicatePhones.add(entry.getKey() + " appears " + ids.size() + " times in IDs " + ids);
	        }
	    }

	    for(Entry<String, List<Integer>> entry : nameMap.entrySet()) {
	    	List<Integer> ids = entry.getValue();
	    	if(ids.size()>1) {
	    		duplicateNames.add(entry.getKey() + " appears " + ids.size() + " times in IDs " + ids);
	    	}
	    }
	    
	    
	    if (!duplicateEmails.isEmpty()) {
	        System.out.println("Duplicate Emails: " );
	        for(String email : duplicateEmails) {
	        	System.out.println(email);
	        }
	    } else {
	        System.out.println("No duplicate emails found.");
	    }

	    if (!duplicatePhones.isEmpty()) {
	        System.out.println("Duplicate Phones: " );
	        for(String phone_number:duplicatePhones) {
	        	System.out.println(phone_number);
	        }
	    } else {
	        System.out.println("No duplicate phones found.");
	    }
	    
	    if (!duplicateNames.isEmpty()) {
	        System.out.println("Duplicate Names: " );
	        for(String names:duplicateNames) {
	        	System.out.println(names);
	        }
	    } else {
	        System.out.println("No duplicate Names found.");
	    }
	    
	    
	    if(duplicateEmails.isEmpty() && duplicatePhones.isEmpty() && duplicateNames.isEmpty()) {
	    	return employees;
	    	
	    }
	    else {
			return new ArrayList<>();
		}
	}
}
