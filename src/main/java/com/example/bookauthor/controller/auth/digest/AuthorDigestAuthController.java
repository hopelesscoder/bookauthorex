package com.example.bookauthor.controller.auth.digest;

import com.example.bookauthor.Author;
import com.example.bookauthor.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/auth/digest")
public class AuthorDigestAuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthorDigestAuthController.class);

	@Autowired
	private AuthorService authorService;

	private ResponseEntity checkAuthorization(String authorization){
		if(authorization == null || !authorization.startsWith("Digest")){
			HttpHeaders respondeHeaders = new HttpHeaders();
			respondeHeaders.add("WWW-Authenticate", "Digest realm=\"authors\" nonce=\"1200\"");
			return new ResponseEntity<>(respondeHeaders, HttpStatus.UNAUTHORIZED);
		}else{
			System.out.println("Authorization = "+authorization);
			if(!authorization.contains("50ee1f19f778938e9d8758c43925e90e")){
				HttpHeaders respondeHeaders = new HttpHeaders();
				respondeHeaders.add("WWW-Authenticate", "Basic realm=\"authors\"");
				return new ResponseEntity<>(respondeHeaders, HttpStatus.FORBIDDEN);
			}
		}
		return null;
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
}
