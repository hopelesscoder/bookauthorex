package com.example.bookauthor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author daniele pasquini
 *
 */
@Service
public class AuthorService {
	
	private static final Logger log = LoggerFactory.getLogger(AuthorService.class);
	
	@Autowired
	private AuthorRespository authorRepository;

	public List<Author> listaAuthor(){
		return authorRepository.findAll();
	}
	
	public Author dettaglioAuthor( int id){
		Optional<Author> author = authorRepository.findById(id);
		if(author.isPresent()) {
			return author.get();
		}else {
			return new Author();
		}
	}
	
	public Author addAuthor(Author author){
		authorRepository.save(author);
		return author;
	}

	public void setAuthorRepository(AuthorRespository authorRepository) {
		this.authorRepository = authorRepository;
	}
}