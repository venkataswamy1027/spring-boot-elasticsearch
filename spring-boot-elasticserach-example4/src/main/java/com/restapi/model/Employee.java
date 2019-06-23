package com.restapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

//Elastic search annotation.
@Document(indexName = "employeedata", type = "employee")
@Data
public class Employee {
	@Id
	private String id;
	private String name;
	private String designation;
}
