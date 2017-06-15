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
import bis.project.security.User;
import bis.project.services.CredentialsServices;

@RestController
public class CredentialsController {
	
	@Autowired
	private CredentialsServices services;
	
	@RequestMapping(value = "/api/login", 
					method = RequestMethod.POST)
	public ResponseEntity<Credentials> login(@RequestBody Credentials credentials) {
		User user = services.login(credentials);
		//@CookieValue("cookie") String cookie
		
		if(user != null) {
			Credentials c = new Credentials(user.getEmail(), user.getPassword());
			String csrfToken = UUID.randomUUID().toString();
			String jwt = services.createJWT(user.getEmail(), csrfToken, user.getSalt());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(HttpHeaders.SET_COOKIE, "jwt=" + jwt + "; Secure; HttpOnly");
			responseHeaders.set("CsrfToken", csrfToken);
			
			return new ResponseEntity<Credentials>(c, responseHeaders, HttpStatus.OK);
		}
		
		return new ResponseEntity<Credentials>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/api/logout", 
					method = RequestMethod.GET)
	public ResponseEntity<String> logout() {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.SET_COOKIE, "jwt=null; Secure; HttpOnly");
		
		return new ResponseEntity<String>("Logout", responseHeaders, HttpStatus.OK);
	}
}
