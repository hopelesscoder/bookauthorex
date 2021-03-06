package com.example.bookauthor.controller.auth.bearer;

import com.example.bookauthor.model.Author;
import com.example.bookauthor.service.AuthorService;
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
public class AuthorBearerAuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthorBearerAuthController.class);

	@Autowired
	private AuthorService authorService;

	private ResponseEntity checkAuthorization(String authorization){
		if(authorization == null || !authorization.startsWith("Bearer")){
			HttpHeaders respondeHeaders = new HttpHeaders();
			respondeHeaders.add("WWW-Authenticate", "Bearer realm=\"authors\"");
			return new ResponseEntity<>(respondeHeaders, HttpStatus.UNAUTHORIZED);
		}else{
			if(!authorization.contains("Q0YjQ2MzEyNzY5YmI5Mj.QUFBQUFBQUFBQUkvQ.Ma60xSyucPupFivm")){
				HttpHeaders respondeHeaders = new HttpHeaders();
				respondeHeaders.add("WWW-Authenticate", "Bearer realm=\"authors\"");
				return new ResponseEntity<>(respondeHeaders, HttpStatus.FORBIDDEN);
			}
		}
		return null;
	}

	private ResponseEntity checkAuthorizationForLogin(String authorization){
		if(authorization == null || !authorization.startsWith("Basic")){
			HttpHeaders respondeHeaders = new HttpHeaders();
			respondeHeaders.add("WWW-Authenticate", "Basic realm=\"authors\"");
			return new ResponseEntity<>(respondeHeaders, HttpStatus.UNAUTHORIZED);
		}else{
			System.out.println("Authorization = "+authorization);
			System.out.println("Credentials = "+authorization.substring(6));
			String userPass = new String(Base64.getDecoder().decode(authorization.substring(6)));
			System.out.println("decoded userpass = "+userPass);
			if(!userPass.equals("adminAuthor:adminPassword")){
				HttpHeaders respondeHeaders = new HttpHeaders();
				respondeHeaders.add("WWW-Authenticate", "Basic realm=\"authors\"");
				return new ResponseEntity<>(respondeHeaders, HttpStatus.FORBIDDEN);
			}
		}
		return null;
	}

	@RequestMapping(value = "/author/login", method = RequestMethod.GET)
	public ResponseEntity<String> login(@RequestHeader(value="Authorization", required = false) String authorization){
		ResponseEntity responseEntity = checkAuthorizationForLogin(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		String response = "{\"access_token\":\"Q0YjQ2MzEyNzY5YmI5Mj.QUFBQUFBQUFBQUkvQ.Ma60xSyucPupFivm\",\"token_type\":\"Bearer\"}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json");
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/author", method = RequestMethod.GET)
	public ResponseEntity<List<Author>> listAuthor(@RequestHeader(value="Authorization", required = false) String authorization){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		List<Author> authors = authorService.findAll();
		if(authors.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(authors, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/author/{id}", method = RequestMethod.GET)
	public ResponseEntity<Author> detailAuthor(@RequestHeader(value="Authorization", required = false) String authorization,
											   @PathVariable int id){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		Optional<Author> author = authorService.findById(id);
		if(author.isPresent()) {
			return new ResponseEntity<>(author.get(), new HttpHeaders(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/author", method = RequestMethod.POST)
	public ResponseEntity<Author> addAuthor(@RequestHeader(value="Authorization", required = false) String authorization,
											@RequestBody Author author){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		Author insertedAuthor = authorService.saveOrUpdateAuthor(author);
		return new ResponseEntity<>(insertedAuthor, new HttpHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/author/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Author> updateAuthor(@RequestHeader(value="Authorization", required = false) String authorization,
											   @PathVariable int id,
											   @RequestBody Author author){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		Optional<Author> authorOpt = authorService.findById(id);
		if(!authorOpt.isPresent()) {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		author.setId(id);
		Author updatedAuthor = authorService.saveOrUpdateAuthor(author);
		return new ResponseEntity<>(updatedAuthor, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/author/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteAuthor(@RequestHeader(value="Authorization", required = false) String authorization,
												   @PathVariable int id){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		authorService.deleteAuthorById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
