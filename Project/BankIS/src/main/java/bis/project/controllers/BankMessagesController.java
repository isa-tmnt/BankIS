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

import bis.project.model.BankMessages;
import bis.project.services.BankMessagesServices;
import bis.project.services.CredentialsServices;

@RestController
public class BankMessagesController {
	
	@Autowired
	private BankMessagesServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/messages", 
					method = RequestMethod.GET)
	public Set<BankMessages> getAllBankMessages() {
		Set<BankMessages> messages = services.getBankMessages();
		return messages;
	}
	
	@RequestMapping(value = "/api/messages/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<BankMessages> getBankMessage(@PathVariable("id") Integer id) {
		BankMessages message =  services.getBankMessage(id);
		
		if(message != null) {
			return new ResponseEntity<BankMessages>(message, HttpStatus.OK);
		}
		
		return new ResponseEntity<BankMessages>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/api/messages", 
					method = RequestMethod.POST)
	public ResponseEntity<BankMessages> addBankMessage(@RequestBody BankMessages message, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "AddBankMessage");
		
		if(isAuthorized) {
			BankMessages m = services.addBankMessage(message);
			return new ResponseEntity<BankMessages>(m, HttpStatus.OK);
		}
		
		return new ResponseEntity<BankMessages>(HttpStatus.UNAUTHORIZED); 
	}
	
	//need to add check for this id
	@RequestMapping(value = "/api/messages/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<BankMessages> updateBankMessage(@PathVariable("id") Integer id, @RequestBody BankMessages message, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "UpdateBankMessage");
		
		if(isAuthorized) {
			message.setId(id);
			BankMessages m = services.updateBankMessage(message);
			return new ResponseEntity<BankMessages>(m, HttpStatus.OK);
		}
		
		return new ResponseEntity<BankMessages>(HttpStatus.UNAUTHORIZED); 
	}
	
	@RequestMapping(value = "/api/messages/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<BankMessages> deleteBankMessage(@PathVariable("id") Integer id, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "DeleteBankMessage");
		
		if(isAuthorized) {
			services.deleteBankMessage(id);
			return new ResponseEntity<BankMessages>(HttpStatus.OK);
		}
		
		return new ResponseEntity<BankMessages>(HttpStatus.UNAUTHORIZED);
	}
}
