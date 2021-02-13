package com.example.bookauthor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * @author daniele pasquini
 *
 */
@RestController
@RequestMapping(value = "/auth/digest")
public class BookDigestAuthController {
	
	private static final Logger log = LoggerFactory.getLogger(BookDigestAuthController.class);

	@Autowired
	private BookService bookService;

	private ResponseEntity checkAuthorization(String authorization){
		if(authorization == null || !authorization.startsWith("Digest")){
			HttpHeaders respondeHeaders = new HttpHeaders();
			respondeHeaders.add("WWW-Authenticate", "Digest realm=\"books\" nonce=\"1200\"");
			return new ResponseEntity<>(respondeHeaders, HttpStatus.UNAUTHORIZED);
		}else{
			System.out.println("Authorization = "+authorization);
			if(!authorization.contains("06ff112d30aba3eeb049032f927eacb8")){
				HttpHeaders respondeHeaders = new HttpHeaders();
				respondeHeaders.add("WWW-Authenticate", "Basic realm=\"books\"");
				return new ResponseEntity<>(respondeHeaders, HttpStatus.FORBIDDEN);
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> listBook(@RequestHeader(value="Authorization", required = false) String authorization){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		List<Book> books = bookService.findAll();
		if(books.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(books, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> detailBook(@RequestHeader(value="Authorization", required = false) String authorization,
										   @PathVariable int id){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		Optional<Book> book = bookService.findById(id);
		if(book.isPresent()) {
			return new ResponseEntity<>(book.get(), new HttpHeaders(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public ResponseEntity<Book> addBook(@RequestHeader(value="Authorization", required = false) String authorization,
										@RequestBody Book book){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		Book insertedBook = bookService.saveOrUpdateBook(book);
		return new ResponseEntity<>(insertedBook, new HttpHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/book/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Book> updateBook(@RequestHeader(value="Authorization", required = false) String authorization,
										   @PathVariable int id,
										   @RequestBody Book book){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		Optional<Book> bookOpt = bookService.findById(id);
		if(!bookOpt.isPresent()) {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		book.setId(id);
		Book updatedBook = bookService.saveOrUpdateBook(book);
		return new ResponseEntity<>(updatedBook, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteBook(@RequestHeader(value="Authorization", required = false) String authorization,
												 @PathVariable int id){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		bookService.deleteBookById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}