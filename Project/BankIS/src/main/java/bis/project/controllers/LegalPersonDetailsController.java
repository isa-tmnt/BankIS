package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.LegalPersonDetails;
import bis.project.services.CredentialsServices;
import bis.project.services.LegalPersonDetailsServices;
import bis.project.validators.LegalPersonDetailsValidator;
import bis.project.validators.ValidationException;

@RestController
public class LegalPersonDetailsController {
	
	@Autowired
	private LegalPersonDetailsServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/legclients", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<LegalPersonDetails>> getAllClients(@RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "getAllClients");
		
		if(isAuthorized) {
			Set<LegalPersonDetails> clients = services.getAllClients();
			return new ResponseEntity<Set<LegalPersonDetails>>(clients, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<LegalPersonDetails>>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/legclients/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<LegalPersonDetails> getClient(@PathVariable("id") Integer id, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "getClient");
		
		if(isAuthorized) {
			LegalPersonDetails client = services.getClient(id);
			
			if(client != null) {
				return new ResponseEntity<LegalPersonDetails>(client, HttpStatus.OK);
			}
			
			return new ResponseEntity<LegalPersonDetails>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<LegalPersonDetails>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/legclients", 
					method = RequestMethod.POST)
	public ResponseEntity<LegalPersonDetails> addClient(@RequestBody LegalPersonDetails client, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "addClient");
		
		if(isAuthorized) {
			LegalPersonDetailsValidator.Validate(client);
			LegalPersonDetails c = services.addClient(client);
			return new ResponseEntity<LegalPersonDetails>(c, HttpStatus.OK);
		}
		
		return new ResponseEntity<LegalPersonDetails>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/legclients/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<LegalPersonDetails> updateClient(@PathVariable("id") Integer id, @RequestBody LegalPersonDetails client, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "updateClient");
		
		if(isAuthorized) {
			LegalPersonDetailsValidator.Validate(client);
			client.setId(id);
			LegalPersonDetails c = services.updateClient(client);
			return new ResponseEntity<LegalPersonDetails>(c, HttpStatus.OK);
		}
		
		return new ResponseEntity<LegalPersonDetails>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/legclients/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<LegalPersonDetails> deleteClient(@PathVariable("id") Integer id, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "deleteClient");
		
		if(isAuthorized) {
			services.deleteClient(id);
			return new ResponseEntity<LegalPersonDetails>(HttpStatus.OK);
		}
		
		return new ResponseEntity<LegalPersonDetails>(HttpStatus.UNAUTHORIZED);
	}
}
