package com.restapi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.dao.BookDao;
import com.restapi.model.Book;

@RestController
@RequestMapping("/api/book")
public class BookController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookDao bookDao;

	@PostMapping
	public Book insertBook(@RequestBody Book book) throws Exception {
		LOGGER.info("Entering into serachByMultiField method {}", System.currentTimeMillis());
		return bookDao.insertBook(book);
	}

	@GetMapping("/{id}")
	public Map<String, Object> getBookById(@PathVariable String id) {
		LOGGER.info("Entering into getBookById method {}", System.currentTimeMillis());
		return bookDao.getBookById(id);
	}

	@PutMapping("/{id}")
	public Map<String, Object> updateBookById(@RequestBody Book book, @PathVariable String id) {
		LOGGER.info("Entering into updateBookById method {}", System.currentTimeMillis());
		return bookDao.updateBookById(id, book);
	}

	@DeleteMapping("/{id}")
	public void deleteBookById(@PathVariable String id) {
		LOGGER.info("Entering into deleteBookById method {}", System.currentTimeMillis());
		bookDao.deleteBookById(id);
	}
}
