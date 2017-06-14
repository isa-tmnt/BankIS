package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.ClientDetails;
import bis.project.services.ClientDetailsServices;
import bis.project.services.CredentialsServices;
import bis.project.validators.ClientDetailsValidator;
import bis.project.validators.ValidationException;

@RestController
public class ClientDetailsController {
	
	@Autowired
	private ClientDetailsServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/clients", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<ClientDetails>> getAllClients(@RequestHeader(value="CsrfToken") String csrfToken, 
															@RequestHeader(value="AuthEmail") String authEmail, 
															@CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "getAllClients");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "getAllClients");
		
		if(isAuthorized) {
			Set<ClientDetails> clients = services.getAllClients();
			return new ResponseEntity<Set<ClientDetails>>(clients, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<ClientDetails>>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/clients/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<ClientDetails> getClient(@PathVariable("id") Integer id, 
												   @RequestHeader(value="CsrfToken") String csrfToken, 
												   @RequestHeader(value="AuthEmail") String authEmail, 
												   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "getClient");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "getClient");
		
		if(isAuthorized) {
			ClientDetails  client = services.getClient(id);
			
			if(client != null) {
				return new ResponseEntity<ClientDetails>(client, HttpStatus.OK);
			}
			
			return new ResponseEntity<ClientDetails>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<ClientDetails>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/clients", 
					method = RequestMethod.POST)
	public ResponseEntity<ClientDetails> addClient(@RequestBody ClientDetails client, 
												   @RequestHeader(value="CsrfToken") String csrfToken, 
												   @RequestHeader(value="AuthEmail") String authEmail, 
												   @CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "addClient");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "addClient");
		
		if(isAuthorized) {
			ClientDetailsValidator.Validate(client);
			ClientDetails c = services.addClient(client);
			return new ResponseEntity<ClientDetails>(c, HttpStatus.OK);
		}
		
		return new ResponseEntity<ClientDetails>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/clients/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<ClientDetails> updateClient(@PathVariable("id") Integer id, 
													  @RequestBody ClientDetails client, 
													  @RequestHeader(value="CsrfToken") String csrfToken, 
													  @RequestHeader(value="AuthEmail") String authEmail, 
													  @CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "updateClient");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "updateClient");
		
		if(isAuthorized) {
			ClientDetailsValidator.Validate(client);
			client.setId(id);
			ClientDetails c = services.updateClient(client);
			return new ResponseEntity<ClientDetails>(c, HttpStatus.OK);
		}
		
		return new ResponseEntity<ClientDetails>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/clients/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<ClientDetails> deleteClient(@PathVariable("id") Integer id, 
													  @RequestHeader(value="CsrfToken") String csrfToken, 
													  @RequestHeader(value="AuthEmail") String authEmail, 
													  @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "deleteClient");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "deleteClient");
		
		if(isAuthorized) {
			services.deleteClient(id);
			return new ResponseEntity<ClientDetails>(HttpStatus.OK);
		}
		
		return new ResponseEntity<ClientDetails>(HttpStatus.UNAUTHORIZED);
	}
}
