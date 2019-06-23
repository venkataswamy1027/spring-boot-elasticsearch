package com.restapi.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.model.Book;

@Repository
public class BookDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookDao.class);

	private final String INDEX = "bookdata";
	private final String TYPE = "books";

	private RestHighLevelClient restHighLevelClient;

	private ObjectMapper objectMapper;

	public BookDao(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
		this.objectMapper = objectMapper;
		this.restHighLevelClient = restHighLevelClient;
	}

	@SuppressWarnings("unchecked")
	public Book insertBook(Book book) {
		LOGGER.info("Entering into insertBook method {}", System.currentTimeMillis());
		book.setId(UUID.randomUUID().toString());
		Map<String, Object> dataMap = objectMapper.convertValue(book, Map.class);
		LOGGER.info("dataMap {}", dataMap);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, book.getId()).source(dataMap);
		try {
			IndexResponse response = restHighLevelClient.index(indexRequest);
			LOGGER.info("response {}", response);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return book;
	}

	public Map<String, Object> getBookById(String id) {
		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		LOGGER.info("getRequest {}", getRequest);
		GetResponse getResponse = null;
		try {
			getResponse = restHighLevelClient.get(getRequest);
			LOGGER.info("getResponse {}", getResponse);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
		LOGGER.info("sourceAsMap {}", sourceAsMap);
		return sourceAsMap;
	}

	public Map<String, Object> updateBookById(String id, Book book) {
		// Fetch Object after its update
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id).fetchSource(true);
		LOGGER.info("updateRequest {}", updateRequest);
		Map<String, Object> error = new HashMap<>();
		error.put("Error", "Unable to update book");
		try {
			String bookJson = objectMapper.writeValueAsString(book);
			updateRequest.doc(bookJson, XContentType.JSON);
			UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
			LOGGER.info("updateResponse {}", updateResponse);
			Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
			LOGGER.info("sourceAsMap {}", sourceAsMap);
			return sourceAsMap;
		} catch (JsonProcessingException e) {
			e.getMessage();
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return error;
	}

	public void deleteBookById(String id) {
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		LOGGER.info("deleteRequest {}", deleteRequest);
		try {
			DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
			LOGGER.info("deleteResponse {}", deleteResponse);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
	}
}
