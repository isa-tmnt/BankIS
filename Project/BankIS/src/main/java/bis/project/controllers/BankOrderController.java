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

import bis.project.model.Bank;
import bis.project.model.BankOrder;
import bis.project.services.BankOrderServices;
import bis.project.services.BankServices;
import bis.project.services.CredentialsServices;
import bis.project.validators.BankOrderValidator;
import bis.project.validators.BankValidator;
import bis.project.validators.ValidationException;

@RestController
public class BankOrderController {
	
	@Autowired
	private BankOrderServices bankOrderServices;
	
	@Autowired
	private CredentialsServices services;
	
	@RequestMapping(value = "/api/bankOrders", 
					method = RequestMethod.GET)
	public Set<BankOrder> getAllBanks() {
		Set<BankOrder> bankOrders = bankOrderServices.getBankOrders();
		return bankOrders;
	}
	
	@RequestMapping(value = "/api/bankOrders/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<BankOrder> getBankOrder(@PathVariable("id") Integer id) {
		BankOrder bank = bankOrderServices.getBankOrder(id);
		
		if(bank == null) {
			return new ResponseEntity<BankOrder>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<BankOrder>(bank, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/bankOrders", 
					method = RequestMethod.POST)
	public ResponseEntity<BankOrder> addBankOrder(@RequestBody BankOrder bank, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException {
		boolean isAuthorized = services.isAuthorized(basicAuth, "AddBankOrder");
		
		if(isAuthorized) {
			BankOrderValidator.Validate(bank);
			BankOrder b = bankOrderServices.addBankOrder(bank);
			return new ResponseEntity<BankOrder>(b, HttpStatus.OK);
		}
		
		return new ResponseEntity<BankOrder>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/bankOrders/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<BankOrder> updateBankOrder(@PathVariable("id") Integer id, @RequestBody BankOrder bank, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException {
		boolean isAuthorized = services.isAuthorized(basicAuth, "UpdateBankOrder");
		
		if(isAuthorized) {
			BankOrderValidator.Validate(bank);
			bank.setId(id);
			BankOrder b = bankOrderServices.updateBankOrder(bank);
			return new ResponseEntity<BankOrder>(b, HttpStatus.OK);
		}
		
		return new ResponseEntity<BankOrder>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/bankOrders/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<BankOrder> deleteBankOrder(@PathVariable("id") Integer id, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = services.isAuthorized(basicAuth, "DeleteBankOrder");
		
		if(isAuthorized) {
			bankOrderServices.deleteBankOrder(id);
			return new ResponseEntity<BankOrder>(HttpStatus.OK);
		}
		
		return new ResponseEntity<BankOrder>(HttpStatus.UNAUTHORIZED);
	}
}
