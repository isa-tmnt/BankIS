package bis.project.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.Bank;
import bis.project.services.BankServices;
import bis.project.validators.BankValidator;
import bis.project.validators.ValidationException;

@RestController
public class BankController {
	
	@Autowired
	private BankServices bankServices;
	
	@RequestMapping(value = "/api/banks", 
					method = RequestMethod.GET)
	public Set<Bank> getAllBanks() {
		Set<Bank> banks = bankServices.getAllBanks();
		return banks;
	}
	
	@RequestMapping(value = "/api/banks/{id}", 
					method = RequestMethod.GET)
	public Bank getBank(@PathVariable("id") Integer id) {
		return bankServices.getBank(id);
	}
	
	@RequestMapping(value = "/api/banks", 
					method = RequestMethod.POST)
	public Bank addBank(@Valid @RequestBody Bank bank) throws ValidationException {
		BankValidator.Validate(bank);
		return bankServices.addBank(bank);
	}
	
	@RequestMapping(value = "/api/banks/{id}", 
					method = RequestMethod.PUT)
	public Bank updateBank(@PathVariable("id") Integer id, @RequestBody Bank bank) {
		bank.setId(id);
		return bankServices.updateBank(bank);
	}
	
	@RequestMapping(value = "/api/banks/{id}", 
					method = RequestMethod.DELETE)
	public void deleteBank(@PathVariable("id") Integer id) {
		bankServices.deleteBank(id);
	}
}
