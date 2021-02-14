package com.example.bookauthor.controller.auth.digest;

import com.example.bookauthor.model.Address;
import com.example.bookauthor.service.AddressService;
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
@RequestMapping(value = "/auth/digest")
public class AddressDigestAuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AddressDigestAuthController.class);

	@Autowired
	private AddressService addressService;

	private ResponseEntity checkAuthorization(String authorization){
		if(authorization == null || !authorization.startsWith("Digest")){
			HttpHeaders respondeHeaders = new HttpHeaders();
			respondeHeaders.add("WWW-Authenticate", "Digest realm=\"addresses\" nonce=\"1200\"");
			return new ResponseEntity<>(respondeHeaders, HttpStatus.UNAUTHORIZED);
		}else{
			System.out.println("Authorization = "+authorization);
			if(!authorization.contains("9a8f48f9a81d9cd08a4cba52dbdddadf")){
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
}
