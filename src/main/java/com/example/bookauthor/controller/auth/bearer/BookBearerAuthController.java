package com.example.bookauthor.controller.auth.bearer;

import com.example.bookauthor.Book;
import com.example.bookauthor.BookService;
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
@RequestMapping(value = "/auth/bearer")
public class BookBearerAuthController {
	
	private static final Logger log = LoggerFactory.getLogger(BookBearerAuthController.class);

	@Autowired
	private BookService bookService;

	private ResponseEntity checkAuthorization(String authorization){
		if(authorization == null || !authorization.startsWith("Bearer")){
			HttpHeaders respondeHeaders = new HttpHeaders();
			respondeHeaders.add("WWW-Authenticate", "Bearer realm=\"books\"");
			return new ResponseEntity<>(respondeHeaders, HttpStatus.UNAUTHORIZED);
		}else{
			if(!authorization.contains("YmIiLCJ0eXAiO.ljdHVyZSI6Imh.2hf2BpGqi4rS")){
				HttpHeaders respondeHeaders = new HttpHeaders();
				respondeHeaders.add("WWW-Authenticate", "Bearer realm=\"books\"");
				return new ResponseEntity<>(respondeHeaders, HttpStatus.FORBIDDEN);
			}
		}
		return null;
	}

	private ResponseEntity checkAuthorizationForLogin(String authorization){
		if(authorization == null || !authorization.startsWith("Basic")){
			HttpHeaders respondeHeaders = new HttpHeaders();
			respondeHeaders.add("WWW-Authenticate", "Basic realm=\"books\"");
			return new ResponseEntity<>(respondeHeaders, HttpStatus.UNAUTHORIZED);
		}else{
			System.out.println("Authorization = "+authorization);
			System.out.println("Credentials = "+authorization.substring(6));
			String userPass = new String(Base64.getDecoder().decode(authorization.substring(6)));
			System.out.println("decoded userpass = "+userPass);
			if(!userPass.equals("adminBook:adminPassword")){
				HttpHeaders respondeHeaders = new HttpHeaders();
				respondeHeaders.add("WWW-Authenticate", "Basic realm=\"books\"");
				return new ResponseEntity<>(respondeHeaders, HttpStatus.FORBIDDEN);
			}
		}
		return null;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<String> login(@RequestHeader(value="Authorization", required = false) String authorization){
		ResponseEntity responseEntity = checkAuthorizationForLogin(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		String response = "{\"access_token\":\"YmIiLCJ0eXAiO.ljdHVyZSI6Imh.2hf2BpGqi4rS\",\"token_type\":\"Bearer\"}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json");
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
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
