package com.restapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(indexName = "mkyong", type = "books")
@Data
@AllArgsConstructor
public class Book {
	@Id
	private String id;
	private String title;
	private String author;
	private String releaseDate;
}
