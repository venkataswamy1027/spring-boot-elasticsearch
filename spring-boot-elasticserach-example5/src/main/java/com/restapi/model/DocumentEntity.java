package com.restapi.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

//Elastic search annotation.
@Document(indexName = "documentdata", type = "document")
@Data
public class DocumentEntity {
	@Id
	private String id;
	private String docType;
	private String docTitle;
}
