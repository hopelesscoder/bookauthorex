package com.example.bookauthor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

	public List<Book> findAll(Specification spec){
		return bookRepository.findAll(spec);
	}
	
	public Optional<Book> findById( int id){
		Optional<Book> book = bookRepository.findById(id);
		return book;
	}
	
	public Book saveOrUpdateBook(Book book){
		return bookRepository.save(book);
	}

	public void deleteBookById(int id){
		bookRepository.deleteById(id);
	}

	public void setBookRepository(BookRespository bookRepository) {
		this.bookRepository = bookRepository;
	}
}