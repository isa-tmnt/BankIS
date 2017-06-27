package cagen.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cagen.project.model.KeyStoreDTO;
import cagen.project.services.CredentialsServices;
import cagen.project.services.KeyStoreServices;

@RestController
public class KeyStoreController {
	
	@Autowired
	private KeyStoreServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/keystore", 
					method = RequestMethod.POST)
	public ResponseEntity<String> genKeyStore(@RequestBody KeyStoreDTO dto, 
											  @RequestHeader(value="CsrfToken") String csrfToken, 
											  @RequestHeader(value="AuthEmail") String authEmail, 
											  @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "genKeyStore");
		
		if(isAuthorized) {
			KeyStoreDTO keyStore = services.genKeyStore(dto);
			
			if(keyStore != null) {
				return new ResponseEntity<String>(HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("KeyStore wasn't generated!", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
}
