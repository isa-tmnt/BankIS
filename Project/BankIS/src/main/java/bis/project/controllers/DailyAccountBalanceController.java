package bis.project.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.Currency;
import bis.project.model.DailyAccountBalance;
import bis.project.services.CredentialsServices;
import bis.project.services.CurrencyServices;
import bis.project.services.DailyAccountBalanceServices;
import bis.project.validators.CurrencyValidator;
import bis.project.validators.ValidationException;

@RestController
public class DailyAccountBalanceController {
	@Autowired
	private DailyAccountBalanceServices dabServices;
	
	@Autowired
	private CredentialsServices services;
	
	@RequestMapping(value = "/api/dailyAccountBalances", 
					method = RequestMethod.GET)
	public Set<DailyAccountBalance> getAllDailyAccountBalances(@RequestHeader(value="BankId") Integer bankId) {
		Set<DailyAccountBalance> dds = dabServices.getAllDailyAccountBalances();
		if(bankId != null){
			dds = dds.stream().filter(x -> x.getAccount().getBank().getId().equals(bankId)).collect(Collectors.toSet());
		}
		return dds;
	}
	
	@RequestMapping(value = "/api/dailyAccountBalances/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<DailyAccountBalance> getDailyAccountBalance(@PathVariable("id") Integer id) {
		DailyAccountBalance ddd = dabServices.getDailyAccountBalance(id);
		
		if(ddd != null) {
			return new ResponseEntity<DailyAccountBalance>(ddd, HttpStatus.OK);
		}
		
		return new ResponseEntity<DailyAccountBalance>(HttpStatus.NOT_FOUND);
	}
	
}
