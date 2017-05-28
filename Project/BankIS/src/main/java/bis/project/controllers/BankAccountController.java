package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.BankAccount;
import bis.project.services.BankAccountServices;

@RestController
public class BankAccountController {
	
	@Autowired
	private BankAccountServices services;
	
	@RequestMapping(value = "/api/accounts", 
					method = RequestMethod.GET)
	public Set<BankAccount> getAllBankAccounts() {
		Set<BankAccount> accounts = services.getAllBankAccounts();
		return accounts;
	}
	
	@RequestMapping(value = "/api/accounts/{id}", 
					method = RequestMethod.GET)
	public BankAccount getBankAccount(@PathVariable("id") Integer id) {
		return services.getBankAccount(id);
	}
	
	@RequestMapping(value = "/api/accounts", 
					method = RequestMethod.POST)
	public BankAccount addBankAccount(@RequestBody BankAccount account) {
		return services.addBankAccount(account);
	}
	
	@RequestMapping(value = "/api/accounts/{id}", 
					method = RequestMethod.PUT)
	public BankAccount updateBankAccount(@PathVariable("id") Integer id, @RequestBody BankAccount account) {
		account.setId(id);
		return services.updateBankAccount(account);
	}
	
	@RequestMapping(value = "/api/accounts/{id}", 
					method = RequestMethod.DELETE)
	public void deleteBankAccount(@PathVariable("id") Integer id) {
		services.deleteBankAccount(id);
	}
}
