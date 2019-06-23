package com.restapi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.restapi.model.Book;
import com.restapi.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceTest.class);
	
	@Autowired
	private BookService bookService;

	@Autowired
	private ElasticsearchTemplate esTemplate;

	@Before
	public void before() {
		LOGGER.info("Entering into before method {}", System.currentTimeMillis());
		esTemplate.deleteIndex(Book.class);
		esTemplate.createIndex(Book.class);
		esTemplate.putMapping(Book.class);
		esTemplate.refresh(Book.class);
		LOGGER.info("Exiting on before method {}", System.currentTimeMillis());
	}

	@Test
	public void testSave() {
		LOGGER.info("Entering into testSave method {}", System.currentTimeMillis());
		Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
		Book testBook = bookService.save(book);

		assertNotNull(testBook.getId());
		assertEquals(testBook.getTitle(), book.getTitle());
		assertEquals(testBook.getAuthor(), book.getAuthor());
		assertEquals(testBook.getReleaseDate(), book.getReleaseDate());

	}

	@Test
	public void testFindOne() {
		LOGGER.info("Entering into testFindOne method {}", System.currentTimeMillis());
		Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
		bookService.save(book);

		Book testBook = bookService.findOne(book.getId());

		assertNotNull(testBook.getId());
		assertEquals(testBook.getTitle(), book.getTitle());
		assertEquals(testBook.getAuthor(), book.getAuthor());
		assertEquals(testBook.getReleaseDate(), book.getReleaseDate());
		LOGGER.info("Exiting on testFindOne method {}", System.currentTimeMillis());
	}

	@Test
	public void testFindByTitle() {
		LOGGER.info("Entering into testFindByTitle method {}", System.currentTimeMillis());
		Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
		bookService.save(book);

		List<Book> byTitle = bookService.findByTitle(book.getTitle());
		assertThat(byTitle.size(), is(1));
		LOGGER.info("Exiting on testFindByTitle method {}", System.currentTimeMillis());
	}

	@Test
	public void testFindByAuthor() {
		LOGGER.info("Entering into testFindByAuthor method {}", System.currentTimeMillis());
		List<Book> bookList = new ArrayList<>();

		bookList.add(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
		bookList.add(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
		bookList.add(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));
		bookList.add(new Book("1007", "Spring Data + ElasticSearch", "Rambabu Posa", "01-APR-2017"));
		bookList.add(new Book("1008", "Spring Boot + MongoDB", "Mkyong", "25-FEB-2017"));

		for (Book book : bookList) {
			bookService.save(book);
		}

		Page<Book> byAuthor = bookService.findByAuthor("Rambabu Posa", new PageRequest(0, 10));
		assertThat(byAuthor.getTotalElements(), is(4L));

		Page<Book> byAuthor2 = bookService.findByAuthor("Mkyong", new PageRequest(0, 10));
		assertThat(byAuthor2.getTotalElements(), is(1L));
		LOGGER.info("Exiting on testFindByTitle method {}", System.currentTimeMillis());
	}

	@Test
	public void testDelete() {
		LOGGER.info("Entering into testDelete method {}", System.currentTimeMillis());
		Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
		bookService.save(book);
		bookService.delete(book);
		Book testBook = bookService.findOne(book.getId());
		assertNull(testBook);
		
		LOGGER.info("Exiting on testDelete method {}", System.currentTimeMillis());
	}

}
