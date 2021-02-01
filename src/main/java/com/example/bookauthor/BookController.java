package com.example.bookauthor;

import java.util.List;
import java.util.Map;

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
	
	@RequestMapping(value = "/listaBook", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> listaBook(){
		List<Book> books = bookService.listaBook();
		return new ResponseEntity<List<Book>>(books, new HttpHeaders(), HttpStatus.OK);
		//return bookService.listaBook();
	}
			
	@RequestMapping(value = "/dettaglioBook/{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> dettaglioBook(@PathVariable int id){
		Book book = bookService.dettaglioBook(id);
		return new ResponseEntity<Book>(book, new HttpHeaders(), HttpStatus.OK);
		//return bookService.dettaglioBook(id);
	}
	
	@RequestMapping(value = "/inserisciBook", method = RequestMethod.POST)
	public ResponseEntity<Book> addBook(@RequestBody Book book){
		Book insertedBook = bookService.addBook(book);
		return new ResponseEntity<Book>(insertedBook, new HttpHeaders(), HttpStatus.CREATED);
	}
}
