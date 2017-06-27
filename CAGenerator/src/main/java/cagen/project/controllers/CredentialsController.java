package cagen.project.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cagen.project.security.Credentials;
import cagen.project.security.User;
import cagen.project.security.UserResponse;
import cagen.project.services.CredentialsServices;

@RestController
public class CredentialsController {
	
	@Autowired
	private CredentialsServices services;
	
	@RequestMapping(value = "/api/login", 
					method = RequestMethod.POST)
	public ResponseEntity<UserResponse> login(@RequestBody Credentials credentials) {
		User user = services.login(credentials);
		
		if(user != null) {
			UserResponse response = new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail());
			response.setRoles(user.getRoles());
			
			String csrfToken = UUID.randomUUID().toString();
			String jwt = services.createJWT(user.getEmail(), csrfToken, user.getSalt());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(HttpHeaders.SET_COOKIE, "jwt=" + jwt + "; Secure; HttpOnly");
			responseHeaders.set("CsrfToken", csrfToken);
			
			return new ResponseEntity<UserResponse>(response, responseHeaders, HttpStatus.OK);
		}
		
		return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/api/logout", 
					method = RequestMethod.GET)
	public ResponseEntity<String> logout() {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.SET_COOKIE, "jwt=null; Secure; HttpOnly");
		
		return new ResponseEntity<String>(responseHeaders, HttpStatus.OK);
	}
}
