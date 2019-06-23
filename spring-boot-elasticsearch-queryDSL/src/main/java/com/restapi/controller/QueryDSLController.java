package com.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.Customer;
import com.restapi.service.QueryDSLService;

@RestController
@RequestMapping("/api")
public class QueryDSLController {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryDSLController.class);
	
	@Autowired
	private QueryDSLService service;

	
	@GetMapping("/serachMultiField/{firstname}/{age}")
	public List<Customer> serachByMultiField(@PathVariable String firstname, @PathVariable int age) {
		LOGGER.info("Entering into serachByMultiField method {}", System.currentTimeMillis());
		return service.searchMultiField(firstname, age);
	}

	@GetMapping("/customSearch/{firstName}")
	public List<Customer> getCustomerByField(@PathVariable String firstName) {
		LOGGER.info("Entering into getCustomerByField method {}", System.currentTimeMillis());
		return service.getCustomerSerachData(firstName);
	}

	@GetMapping("/search/{text}")
	public List<Customer> doMultimatchQuery(@PathVariable String text) {
		LOGGER.info("Entering into doMultimatchQuery method {}", System.currentTimeMillis());
		return service.multiMatchQuery(text);
	}

}
