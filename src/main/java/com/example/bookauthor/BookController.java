package com.example.bookauthor;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Book> listaBook(){
		return bookService.listaBook();
	}
			
	@RequestMapping(value = "/dettaglioBook/{id}", method = RequestMethod.GET)
	public Book dettaglioBook(@PathVariable int id){
		return bookService.dettaglioBook(id);
	}
	
	@RequestMapping(value = "/inserisciBook", method = RequestMethod.POST)
	public Book addBook(@RequestBody Book book){
		return bookService.addBook(book);
	}
}
