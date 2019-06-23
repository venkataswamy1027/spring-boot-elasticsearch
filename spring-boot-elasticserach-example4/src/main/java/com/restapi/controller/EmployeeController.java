package com.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.Employee;
import com.restapi.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	/**
	 * Method to save the employees in the database.
	 */
	@PostMapping(value = "/save/employees")
	public String saveEmployee(@RequestBody List<Employee> myemployees) {
		LOGGER.info("Entering into saveEmployee method {}", System.currentTimeMillis());
		employeeService.saveEmployee(myemployees);
		return "Records saved in the db.";
	}

	/**
	 * Method to fetch all employees from the database.
	 */
	@GetMapping(value = "/all/employees")
	public Iterable<Employee> getAllEmployees() {
		LOGGER.info("Entering into getAllEmployees method {}", System.currentTimeMillis());
		return employeeService.findAllEmployees();
	}

	/**
	 * Method to fetch the employee details on the basis of designation.
	 * 
	 * @param designation
	 */
	@GetMapping(value = "/findbydesignation/{employee-designation}")
	public Iterable<Employee> getByDesignation(@PathVariable(name = "employee-designation") String designation) {
		LOGGER.info("Entering into getByDesignation method {}", System.currentTimeMillis());
		return employeeService.findByDesignation(designation);
	}
}
