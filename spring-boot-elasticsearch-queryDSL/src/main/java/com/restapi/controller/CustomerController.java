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

import com.restapi.model.Customer;
import com.restapi.repository.CustomerRepository;

@RestController
@RequestMapping("/api")
public class CustomerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerRepository customerRepository;

	@PostMapping("/saveCustomer")
	public int saveCustomer(@RequestBody List<Customer> customers) {
		LOGGER.info("Entering into saveCustomer method {}", System.currentTimeMillis());
		try {
			customerRepository.save(customers);
			return customers.size();
		} finally {
			LOGGER.info("Exiting on saveCustomer method at {}", System.currentTimeMillis());
		}
	}

	@GetMapping("/findAll")
	public Iterable<Customer> findAllCustomers() {
		LOGGER.info("Entering into findAllCustomers method {}", System.currentTimeMillis());
		try {
			return customerRepository.findAll();
		} finally {
			LOGGER.info("Exiting on findAllCustomers method at {}", System.currentTimeMillis());
		}
	}

	@GetMapping("/findByName/{firstName}")
	public List<Customer> findByFirstName(@PathVariable String firstName) {
		LOGGER.info("Entering into findByFirstName method {}", System.currentTimeMillis());
		try {
			return customerRepository.findByFirstname(firstName);
		} finally {
			LOGGER.info("Exiting on findByFirstName method at {}", System.currentTimeMillis());
		}
	}

}
