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

import bis.project.model.Bank;
import bis.project.services.BankServices;
import bis.project.services.CredentialsServices;
import bis.project.validators.BankValidator;
import bis.project.validators.ValidationException;

@RestController
public class BankController {
	
	@Autowired
	private BankServices bankServices;
	
	@Autowired
	private CredentialsServices services;
	
	@RequestMapping(value = "/api/banks", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<Bank>> getAllBanks(@RequestHeader(value="CsrfToken") String csrfToken, 
												 @RequestHeader(value="AuthEmail") String authEmail, 
												 @RequestHeader(value="BankId") Integer bankId, 
												 @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "GetAllBanks");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "GetAllBanks");
		
		if(isAuthorized) {
			Set<Bank> banks = bankServices.getAllBanks();
			return new ResponseEntity<Set<Bank>>(banks, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<Bank>>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/banks/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<Bank> getBank(@PathVariable("id") Integer id, 
										@RequestHeader(value="CsrfToken") String csrfToken, 
										@RequestHeader(value="AuthEmail") String authEmail, 
										@RequestHeader(value="BankId") Integer bankId, 
										@CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "GetBank");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "GetBank");
		
		if(isAuthorized) {
			Bank bank = bankServices.getBank(id);
			
			if(bank == null) {
				return new ResponseEntity<Bank>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Bank>(bank, HttpStatus.OK);
		}
		
		return new ResponseEntity<Bank>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/banks", 
					method = RequestMethod.POST)
	public ResponseEntity<Bank> addBank(@RequestBody Bank bank, 
										@RequestHeader(value="CsrfToken") String csrfToken, 
										@RequestHeader(value="AuthEmail") String authEmail, 
										@RequestHeader(value="BankId") Integer bankId, 
										@CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "AddBank");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "AddBank");
		
		if(isAuthorized) {
			BankValidator.Validate(bank);
			Bank b = bankServices.addBank(bank);
			return new ResponseEntity<Bank>(b, HttpStatus.OK);
		}
		
		return new ResponseEntity<Bank>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/banks/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<Bank> updateBank(@PathVariable("id") Integer id, @RequestBody Bank bank, 
										   @RequestHeader(value="CsrfToken") String csrfToken, 
										   @RequestHeader(value="AuthEmail") String authEmail, 
										   @RequestHeader(value="BankId") Integer bankId, 
										   @CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "UpdateBank");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "UpdateBank");
		
		if(isAuthorized) {
			BankValidator.Validate(bank);
			bank.setId(id);
			Bank b = bankServices.updateBank(bank);
			return new ResponseEntity<Bank>(b, HttpStatus.OK);
		}
		
		return new ResponseEntity<Bank>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/banks/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<Bank> deleteBank(@PathVariable("id") Integer id, 
										   @RequestHeader(value="CsrfToken") String csrfToken, 
										   @RequestHeader(value="AuthEmail") String authEmail, 
										   @RequestHeader(value="BankId") Integer bankId, 
										   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "DeleteBank");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "DeleteBank");
		
		if(isAuthorized) {
			bankServices.deleteBank(id);
			return new ResponseEntity<Bank>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Bank>(HttpStatus.UNAUTHORIZED);
	}
}
