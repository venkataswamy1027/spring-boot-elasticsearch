package com.restapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.DocumentEntity;
import com.restapi.repository.DocumentRepository;

@RestController
@RequestMapping("/api")
public class DocumentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

	@Autowired
	private DocumentRepository documentRepository;

	@RequestMapping("/")
	public String SpringBootESExample() {
		return "Welcome to Spring Boot Elastic Search Example";
	}

	// Store Documents
	@PostMapping("/save/document")
	public String saveAllDocuments(@RequestBody List<DocumentEntity> documents) {
		LOGGER.info("Entering into saveAllDocuments method {}", System.currentTimeMillis());
		documentRepository.save(documents);
		return documents.size() + " documents saved!!!";
	}

	@GetMapping("/getAll")
	public List<DocumentEntity> getAllDocs() {
		LOGGER.info("Entering into getAllDocs method {}", System.currentTimeMillis());
		List<DocumentEntity> documents = new ArrayList<>();
		// iterate all documents and add it to list
		for (DocumentEntity doc : documentRepository.findAll()) {
			documents.add(doc);
		}
		return documents;
	}

	@DeleteMapping("/delete")
	public String deleteAllDocuments() {
		LOGGER.info("Entering into deleteAllDocuments method {}", System.currentTimeMillis());
		try { // delete all documents
			documentRepository.deleteAll();
			return "documents deleted succesfully!";
		} catch (Exception e) {
			return "Failed to delete documents";
		}
	}
}
