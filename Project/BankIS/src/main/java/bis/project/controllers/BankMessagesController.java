package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.BankMessages;
import bis.project.services.BankMessagesServices;
import bis.project.validators.BankMessagesValidator;
import bis.project.validators.ValidationException;

@RestController
public class BankMessagesController {
	
	@Autowired
	private BankMessagesServices services;
	
	@RequestMapping(value = "/api/messages", 
					method = RequestMethod.GET)
	public Set<BankMessages> getAllBankMessages() {
		Set<BankMessages> messages = services.getBankMessages();
		return messages;
	}
	
	@RequestMapping(value = "/api/messages/{id}", 
					method = RequestMethod.GET)
	public BankMessages getBankMessage(@PathVariable("id") Integer id) {
		return services.getBankMessage(id);
	}
	
	@RequestMapping(value = "/api/messages", 
					method = RequestMethod.POST)
	public BankMessages addBankMessage(@RequestBody BankMessages message)  throws ValidationException {
		BankMessagesValidator.Validate(message);
		return services.addBankMessage(message);
	}
	
	@RequestMapping(value = "/api/messages/{id}", 
					method = RequestMethod.PUT)
	public BankMessages updateBankMessage(@PathVariable("id") Integer id, @RequestBody BankMessages message)  throws ValidationException {
		BankMessagesValidator.Validate(message);
		message.setId(id);
		return services.updateBankMessage(message);
	}
	
	@RequestMapping(value = "/api/messages/{id}", 
					method = RequestMethod.DELETE)
	public void deleteBankMessage(@PathVariable("id") Integer id) {
		services.deleteBankMessage(id);
	}
}
