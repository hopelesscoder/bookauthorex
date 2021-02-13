package com.example.bookauthor.controller.auth.digest;

import com.example.bookauthor.Book;
import com.example.bookauthor.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
