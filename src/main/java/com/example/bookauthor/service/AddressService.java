package com.example.bookauthor.service;

import com.example.bookauthor.model.Address;
import com.example.bookauthor.model.Author;
import com.example.bookauthor.repository.AddressRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author daniele pasquini
 *
 */
@Service
public class AddressService extends GenericService<AddressRespository, Address>{
	
	private static final Logger log = LoggerFactory.getLogger(AddressService.class);

	public List<Address> findAllWithfilters(Specification spec){
		return getRepo().findAll(spec);
	}
}