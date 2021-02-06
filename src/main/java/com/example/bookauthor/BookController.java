package com.example.bookauthor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daniele pasquini
 *
 */
@RestController
public class BookController {
	
	private static final Logger log = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> listBook(){
		List<Book> books = bookService.findAll();
		if(books.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(books, new HttpHeaders(), HttpStatus.OK);
	}
			
	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> detailBook(@PathVariable int id){
		Optional<Book> book = bookService.findById(id);
		if(book.isPresent()) {
			return new ResponseEntity<>(book.get(), new HttpHeaders(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public ResponseEntity<Book> addBook(@RequestBody Book book){
		Book insertedBook = bookService.saveOrUpdateBook(book);
		return new ResponseEntity<>(insertedBook, new HttpHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/book/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book){
		Optional<Book> bookOpt = bookService.findById(id);
		if(!bookOpt.isPresent()) {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		book.setId(id);
		Book updatedBook = bookService.saveOrUpdateBook(book);
		return new ResponseEntity<>(updatedBook, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable int id){
		bookService.deleteBookById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
