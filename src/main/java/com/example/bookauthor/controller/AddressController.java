package com.example.bookauthor.controller;

import com.example.bookauthor.Address;
import com.example.bookauthor.AddressService;
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
public class AddressController {
	
	private static final Logger log = LoggerFactory.getLogger(AddressController.class);

	@Autowired
	private AddressService addressService;
	
	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public ResponseEntity<List<Address>> listAddress(){
		List<Address> addresses = addressService.findAll();
		if(addresses.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(addresses, new HttpHeaders(), HttpStatus.OK);
	}
			
	@RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
	public ResponseEntity<Address> detailAddress(@PathVariable int id){
		Optional<Address> address = addressService.findById(id);
		if(address.isPresent()) {
			return new ResponseEntity<>(address.get(), new HttpHeaders(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value = "/address", method = RequestMethod.POST)
	public ResponseEntity<Address> addAddress(@RequestBody Address address){
		Address insertedAddress = addressService.save(address);
		return new ResponseEntity<>(insertedAddress, new HttpHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/address/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Address> updateAddress(@PathVariable int id, @RequestBody Address address){
		address.setId(id);
		Optional<Address> updatedAddress = addressService.update(address);
		if(!updatedAddress.isPresent()) {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedAddress.get(), new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteAddress(@PathVariable int id){
		addressService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
