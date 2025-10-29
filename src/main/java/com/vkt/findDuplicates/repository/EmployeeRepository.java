package com.vkt.findDuplicates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vkt.findDuplicates.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	boolean existsByEmail(String email);
	boolean existsByPhone(long phone);
	boolean existsByName(String name);
}
