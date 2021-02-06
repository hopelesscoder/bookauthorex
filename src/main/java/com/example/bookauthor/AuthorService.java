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

	public List<Author> findAll(){
		return authorRepository.findAll();
	}

	public Optional<Author> findById( int id){
		Optional<Author> author = authorRepository.findById(id);
		return author;
	}

	public Author saveOrUpdateAuthor(Author author){
		return authorRepository.save(author);
	}

	public void deleteAuthorById(int id){
		authorRepository.deleteById(id);
	}

	public void setAuthorRepository(AuthorRespository authorRepository) {
		this.authorRepository = authorRepository;
	}
}