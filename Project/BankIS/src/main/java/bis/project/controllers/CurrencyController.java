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

import bis.project.model.Currency;
import bis.project.services.CredentialsServices;
import bis.project.services.CurrencyServices;
import bis.project.validators.CurrencyValidator;
import bis.project.validators.ValidationException;

@RestController
public class CurrencyController {
	
	@Autowired
	private CurrencyServices currencyServices;
	
	@Autowired
	private CredentialsServices services;
	
	@RequestMapping(value = "/api/currencies", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<Currency>> getAllCurrencies(@RequestHeader(value="CsrfToken") String csrfToken, 
													   	  @RequestHeader(value="AuthEmail") String authEmail, 
													   	  @RequestHeader(value="BankId") Integer bankId, 
													   	  @CookieValue("jwt") String jwt) {
		boolean isAuthorized = false;
		isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "GetAllCurrencies");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "GetAllCurrencies");
		
		if(isAuthorized) {
			Set<Currency> currencies = currencyServices.getAllCurrency();
			return new ResponseEntity<Set<Currency>>(currencies, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<Currency>>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/currencies/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<Currency> getCurrency(@PathVariable("id") Integer id, 
												@RequestHeader(value="CsrfToken") String csrfToken, 
												@RequestHeader(value="AuthEmail") String authEmail, 
												@RequestHeader(value="BankId") Integer bankId, 
												@CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "getCurrency");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "getCurrency");
		
		if(isAuthorized) {
			Currency currency = currencyServices.getCurrency(id);
			
			if(currency != null) {
				return new ResponseEntity<Currency>(currency, HttpStatus.OK);
			}
			
			return new ResponseEntity<Currency>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Currency>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/currencies", 
					method = RequestMethod.POST)
	public ResponseEntity<Currency> addCurrency(@RequestBody Currency currency, 
												@RequestHeader(value="CsrfToken") String csrfToken, 
												@RequestHeader(value="AuthEmail") String authEmail, 
												@RequestHeader(value="BankId") Integer bankId, 
												@CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = false;
		isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "AddCurrency");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "AddCurrency");
		
		if(isAuthorized) {
			CurrencyValidator.Validate(currency);
			Currency c = currencyServices.addCurrency(currency);
			return new ResponseEntity<Currency>(c, HttpStatus.OK);
		}
		
		return new ResponseEntity<Currency>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/currencies/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<Currency> updateCurrency(@PathVariable("id") Integer id, @RequestBody Currency currency, 
												   @RequestHeader(value="CsrfToken") String csrfToken, 
												   @RequestHeader(value="AuthEmail") String authEmail, 
												   @RequestHeader(value="BankId") Integer bankId, 
												   @CookieValue("jwt") String jwt) throws ValidationException{
		
		boolean isAuthorized = false;
		isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "UpdateCurrency");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "UpdateCurrency");
		
		if(isAuthorized) {
			CurrencyValidator.Validate(currency);
			currency.setId(id);
			Currency c = currencyServices.updateCurrency(currency);
			return new ResponseEntity<Currency>(c, HttpStatus.OK);
		}
		
		return new ResponseEntity<Currency>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/currencies/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<Currency> deleteCurrency(@PathVariable("id") Integer id, 
												   @RequestHeader(value="CsrfToken") String csrfToken, 
												   @RequestHeader(value="AuthEmail") String authEmail, 
												   @RequestHeader(value="BankId") Integer bankId, 
												   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = services.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "DeleteCurrency");
		//boolean isAuthorized = services.isAuthorized(basicAuth, "DeleteCurrency");
		
		if(isAuthorized) {
			currencyServices.deleteCurrency(id);
			return new ResponseEntity<Currency>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Currency>(HttpStatus.UNAUTHORIZED);
	}
}
