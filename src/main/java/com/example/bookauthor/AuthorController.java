package com.example.bookauthor;

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
public class AuthorController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(value = "/listaAuthor", method = RequestMethod.GET)
	public ResponseEntity<List<Author>> listaAuthor(){
		List<Author> authors = authorService.listaAuthor();
		return new ResponseEntity<List<Author>>(authors, new HttpHeaders(), HttpStatus.OK);
	}
			
	@RequestMapping(value = "/dettaglioAuthor/{id}", method = RequestMethod.GET)
	public ResponseEntity<Author> dettaglioAuthor(@PathVariable int id){
		Author authors = authorService.dettaglioAuthor(id);
		return new ResponseEntity<Author>(authors, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inserisciAuthor", method = RequestMethod.POST)
	public ResponseEntity<Author> addAuthor(@RequestBody Author author){
		Author insertedAuthor = authorService.addAuthor(author);
		return new ResponseEntity<Author>(insertedAuthor, new HttpHeaders(), HttpStatus.CREATED);
	}
}
