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

import bis.project.model.BankAccount;
import bis.project.services.BankAccountServices;
import bis.project.services.CredentialsServices;
import bis.project.validators.BankAccountValidator;
import bis.project.validators.ValidationException;

@RestController
public class BankAccountController {
	
	@Autowired
	private BankAccountServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/accounts", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<BankAccount>> getAllBankAccounts(@RequestHeader(value="CsrfToken") String csrfToken, 
															   @RequestHeader(value="AuthEmail") String authEmail, 
															   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "GetAllBankAccounts");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "GetAllBankAccounts");
		
		if(isAuthorized) {
			Set<BankAccount> accounts = services.getAllBankAccounts();
			return new ResponseEntity<Set<BankAccount>>(accounts, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<BankAccount>>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/accounts/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<BankAccount> getBankAccount(@PathVariable("id") Integer id, 
													  @RequestHeader(value="CsrfToken") String csrfToken, 
													  @RequestHeader(value="AuthEmail") String authEmail, 
													  @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "GetBankAccount");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "GetBankAccount");
		
		if(isAuthorized) {
			BankAccount account = services.getBankAccount(id);
			
			if(account != null) {
				return new ResponseEntity<BankAccount>(account, HttpStatus.OK);
			}
			
			return new ResponseEntity<BankAccount>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<BankAccount>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/accounts", 
					method = RequestMethod.POST)
	public ResponseEntity<BankAccount> addBankAccount(@RequestBody BankAccount account, 
													  @RequestHeader(value="CsrfToken") String csrfToken, 
													  @RequestHeader(value="AuthEmail") String authEmail, 
													  @CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "AddBankAccount");
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "AddBankAccount");
		
		if(isAuthorized) {
			BankAccountValidator.Validate(account);
			BankAccount a = services.addBankAccount(account);
			return new ResponseEntity<BankAccount>(a, HttpStatus.OK);
		}
		
		return new ResponseEntity<BankAccount>(HttpStatus.UNAUTHORIZED); 
	}
	
	@RequestMapping(value = "/api/accounts/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<BankAccount> updateBankAccount(@PathVariable("id") Integer id, 
														 @RequestBody BankAccount account, 
														 @RequestHeader(value="CsrfToken") String csrfToken, 
														 @RequestHeader(value="AuthEmail") String authEmail, 
														 @CookieValue("jwt") String jwt)  throws ValidationException{
		
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "UpdateBankAccount");
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "UpdateBankAccount");
		
		if(isAuthorized) {
			BankAccountValidator.Validate(account);
			account.setId(id);
			BankAccount a = services.updateBankAccount(account);
			return new ResponseEntity<BankAccount>(a, HttpStatus.OK);
		}
		
		return new ResponseEntity<BankAccount>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/accounts/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<BankAccount> deleteBankAccount(@PathVariable("id") Integer id, 
														 @RequestHeader(value="CsrfToken") String csrfToken, 
														 @RequestHeader(value="AuthEmail") String authEmail, 
														 @CookieValue("jwt") String jwt) {
		
		//boolean isAuthorized = cServices.isAuthorized(basicAuth, "DeleteBankAccount");
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "DeleteBankAccount");
		
		if(isAuthorized) {
			services.deleteBankAccount(id);
			return new ResponseEntity<BankAccount>(HttpStatus.OK);
		}
		
		return new ResponseEntity<BankAccount>(HttpStatus.UNAUTHORIZED);
	}
}
