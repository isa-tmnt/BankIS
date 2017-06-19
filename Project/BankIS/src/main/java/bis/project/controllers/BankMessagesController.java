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

import bis.project.model.BankMessages;
import bis.project.services.BankMessagesServices;
import bis.project.services.CredentialsServices;
import bis.project.validators.BankMessagesValidator;
import bis.project.validators.ValidationException;

@RestController
public class BankMessagesController {
	
	@Autowired
	private BankMessagesServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/messages", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<BankMessages>> getAllBankMessages(@RequestHeader(value="CsrfToken") String csrfToken, 
																@RequestHeader(value="AuthEmail") String authEmail, 
																@RequestHeader(value="BankId") Integer bankId, 
																@CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "GetAllBankMessages");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "GetAllBankMessages");
		
		if(isAuthorized) {
			Set<BankMessages> messages = services.getBankMessages();
			return new ResponseEntity<Set<BankMessages>>(messages, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<BankMessages>>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/messages/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<BankMessages> getBankMessage(@PathVariable("id") Integer id, 
													   @RequestHeader(value="CsrfToken") String csrfToken, 
													   @RequestHeader(value="AuthEmail") String authEmail, 
													   @RequestHeader(value="BankId") Integer bankId, 
													   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "GetBankMessage");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "GetBankMessage");
		
		if(isAuthorized) {
			BankMessages message =  services.getBankMessage(id);
			
			if(message != null) {
				return new ResponseEntity<BankMessages>(message, HttpStatus.OK);
			}
			
			return new ResponseEntity<BankMessages>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<BankMessages>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/messages", 
					method = RequestMethod.POST)
	public ResponseEntity<BankMessages> addBankMessage(@RequestBody BankMessages message, 
													   @RequestHeader(value="CsrfToken") String csrfToken, 
													   @RequestHeader(value="AuthEmail") String authEmail, 
													   @RequestHeader(value="BankId") Integer bankId, 
													   @CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "AddBankMessage");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "AddBankMessage");
		
		if(isAuthorized) {
			BankMessagesValidator.Validate(message);
			BankMessages m = services.addBankMessage(message);
			return new ResponseEntity<BankMessages>(m, HttpStatus.OK);
		}
		
		return new ResponseEntity<BankMessages>(HttpStatus.UNAUTHORIZED); 
	}
	
	//need to add check for this id
	@RequestMapping(value = "/api/messages/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<BankMessages> updateBankMessage(@PathVariable("id") Integer id, @RequestBody BankMessages message, 
														  @RequestHeader(value="CsrfToken") String csrfToken, 
														  @RequestHeader(value="AuthEmail") String authEmail, 
														  @RequestHeader(value="BankId") Integer bankId, 
														  @CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "UpdateBankMessage");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "UpdateBankMessage");
		
		if(isAuthorized) {
			BankMessagesValidator.Validate(message);
			message.setId(id);
			BankMessages m = services.updateBankMessage(message);
			return new ResponseEntity<BankMessages>(m, HttpStatus.OK);
		}
		
		return new ResponseEntity<BankMessages>(HttpStatus.UNAUTHORIZED); 
	}
	
	@RequestMapping(value = "/api/messages/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<BankMessages> deleteBankMessage(@PathVariable("id") Integer id, 
														  @RequestHeader(value="CsrfToken") String csrfToken, 
														  @RequestHeader(value="AuthEmail") String authEmail, 
														  @RequestHeader(value="BankId") Integer bankId, 
														  @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "DeleteBankMessage");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "DeleteBankMessage");
		
		if(isAuthorized) {
			services.deleteBankMessage(id);
			return new ResponseEntity<BankMessages>(HttpStatus.OK);
		}
		
		return new ResponseEntity<BankMessages>(HttpStatus.UNAUTHORIZED);
	}
}
