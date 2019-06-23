package com.restapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.model.Employee;
import com.restapi.repository.EmployeeRepository;

@Service
public class EmployeeService {

	/*
	 * The employeeRepository will use the Elastic-Search-Repository to perform the
	 * database operations.
	 */
	@Autowired
	private EmployeeRepository employeeRepository;

	public void saveEmployee(List<Employee> employees) {
		employeeRepository.saveAll(employees);
	}

	public Iterable<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	public List<Employee> findByDesignation(String designation) {
		return employeeRepository.findByDesignation(designation);
	}
}
