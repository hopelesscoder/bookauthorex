package com.example.bookauthor.controller.auth.basic;

import com.example.bookauthor.model.Address;
import com.example.bookauthor.service.AddressService;
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
@RequestMapping("/auth/basic")
public class AddressBasicAuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AddressBasicAuthController.class);

	@Autowired
	private AddressService addressService;

	private ResponseEntity checkAuthorization(String authorization){
		if(authorization == null || !authorization.startsWith("Basic")){
			HttpHeaders respondeHeaders = new HttpHeaders();
			respondeHeaders.add("WWW-Authenticate", "Basic realm=\"addresses\"");
			return new ResponseEntity<>(respondeHeaders, HttpStatus.UNAUTHORIZED);
		}else{
			System.out.println("Authorization = "+authorization);
			System.out.println("Credentials = "+authorization.substring(6));
			String userPass = new String(Base64.getDecoder().decode(authorization.substring(6)));
			System.out.println("decoded userpass = "+userPass);
			if(!userPass.equals("adminAddress:adminPassword")){
				HttpHeaders respondeHeaders = new HttpHeaders();
				respondeHeaders.add("WWW-Authenticate", "Basic realm=\"addresses\"");
				return new ResponseEntity<>(respondeHeaders, HttpStatus.FORBIDDEN);
			}
		}
		return null;
	}

	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public ResponseEntity<List<Address>> listAddress(@RequestHeader(value="Authorization", required = false) String authorization){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		List<Address> addresses = addressService.findAll();
		if(addresses.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(addresses, new HttpHeaders(), HttpStatus.OK);
	}
			
	@RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
	public ResponseEntity<Address> detailAddress(@RequestHeader(value="Authorization", required = false) String authorization,
												 @PathVariable int id){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		Optional<Address> address = addressService.findById(id);
		if(address.isPresent()) {
			return new ResponseEntity<>(address.get(), new HttpHeaders(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value = "/address", method = RequestMethod.POST)
	public ResponseEntity<Address> addAddress(@RequestHeader(value="Authorization", required = false) String authorization,
											  @RequestBody Address address){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		Address insertedAddress = addressService.save(address);
		return new ResponseEntity<>(insertedAddress, new HttpHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/address/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Address> updateAddress(@RequestHeader(value="Authorization", required = false) String authorization,
												 @PathVariable int id,
												 @RequestBody Address address){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		address.setId(id);
		Optional<Address> updatedAddress = addressService.update(address);
		if(!updatedAddress.isPresent()) {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedAddress.get(), new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteAddress(@RequestHeader(value="Authorization", required = false) String authorization,
													@PathVariable int id){
		ResponseEntity responseEntity = checkAuthorization(authorization);
		if(responseEntity!= null){
			return responseEntity;
		}
		addressService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
