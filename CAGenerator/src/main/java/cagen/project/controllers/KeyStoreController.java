package cagen.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cagen.project.model.KeyStoreDTO;
import cagen.project.services.KeyStoreServices;

@RestController
public class KeyStoreController {
	
	@Autowired
	private KeyStoreServices services;
	
	@RequestMapping(value = "/api/keystore", 
					method = RequestMethod.POST)
	public ResponseEntity<String> genKeyStore(@RequestBody KeyStoreDTO dto) {
		
		KeyStoreDTO keyStore = services.genKeyStore(dto);
		
		if(keyStore != null) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("KeyStore wasn't generated!", HttpStatus.BAD_REQUEST);
	}
}
