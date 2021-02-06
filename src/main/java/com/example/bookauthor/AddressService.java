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
public class AddressService extends GenericService<AddressRespository, Address>{
	
	private static final Logger log = LoggerFactory.getLogger(AddressService.class);
}