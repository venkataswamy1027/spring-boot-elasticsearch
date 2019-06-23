package com.restapi.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.restapi.model.Employee;

public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {

	/**
	 * Method to fetch the employee details on the basis of designation by using
	 * Elastic-Search-Repository.
	 */
	List<Employee> findByDesignation(String designation);
}
