package com.example.bookauthor.controller;

import com.example.bookauthor.model.Author;
import com.example.bookauthor.model.Book;
import com.example.bookauthor.service.AuthorService;
import com.example.bookauthor.specification.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author daniele pasquini
 *
 */
@RestController
public class AuthorController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

	@Autowired
	private AuthorService authorService;

	@RequestMapping(value = "/author", method = RequestMethod.GET)
	public ResponseEntity<List<Author>> listAuthor(@RequestParam(required = false) String name,
												   @RequestParam(required = false) String surname,
												   @RequestParam(required = false) Integer telephone,
												   @RequestParam(required = false) Integer addressId){
		Specification<Book> spec = Specification.where(new AuthorWithName(name))
				.and(new AuthorWithSurname(surname))
				.and(new AuthorWithTelephone(telephone))
				.and(new AuthorWithAddressId(addressId));
		List<Author> authors = authorService.findAllWithfilters(spec);
		if(authors.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(authors, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/author/{id}", method = RequestMethod.GET)
	public ResponseEntity<Author> detailAuthor(@PathVariable int id){
		Optional<Author> author = authorService.findById(id);
		if(author.isPresent()) {
			return new ResponseEntity<>(author.get(), new HttpHeaders(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/author", method = RequestMethod.POST)
	public ResponseEntity<Author> addAuthor(@RequestBody Author author){
		Author insertedAuthor = authorService.saveOrUpdateAuthor(author);
		return new ResponseEntity<>(insertedAuthor, new HttpHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/author/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author){
		Optional<Author> authorOpt = authorService.findById(id);
		if(!authorOpt.isPresent()) {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		author.setId(id);
		Author updatedAuthor = authorService.saveOrUpdateAuthor(author);
		return new ResponseEntity<>(updatedAuthor, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/author/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable int id){
		authorService.deleteAuthorById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
