package bis.project.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.security.Credentials;
import bis.project.services.CredentialsServices;

@RestController
public class CredentialsController {
	//@RequestHeader(value="Authorization") String basicAuth
	//'Authorization': 'Basic ' + sessionStorage.getItem('basicAuth')
	//'Authorization': 'Basic ' + btoa(email + ':' + password)
	@Autowired
	private CredentialsServices services;
	
	@RequestMapping(value = "/api/login", 
					method = RequestMethod.POST)
	public ResponseEntity<Credentials> login(@RequestBody Credentials credentials) {
		Credentials c = services.login(credentials);
		//@CookieValue("cookie") String cookie
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.SET_COOKIE, "cookie=cookieValue; HttpOnly");
		responseHeaders.set("CSRF-TOKEN", UUID.randomUUID().toString());
		
		if(c != null) {
			return new ResponseEntity<Credentials>(c, responseHeaders, HttpStatus.OK);
		}
		
		return new ResponseEntity<Credentials>(HttpStatus.NOT_FOUND);
	}
}
