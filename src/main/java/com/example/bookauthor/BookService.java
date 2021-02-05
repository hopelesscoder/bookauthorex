package com.example.bookauthor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author daniele pasquini
 *
 */
@Service
public class BookService {
	
	private static final Logger log = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private BookRespository bookRepository;

	public List<Book> listaBook(){
		return bookRepository.findAll();
	}
	
	public Book dettaglioBook( int id){
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()) {
			return book.get();
		}else {
			return new Book();
		}
	}
	
	public Book addBook(Book book){
		bookRepository.save(book);
		return book;
	}

	public void setBookRepository(BookRespository bookRepository) {
		this.bookRepository = bookRepository;
	}
}