package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.LegalPersonDetails;
import bis.project.services.LegalPersonDetailsServices;
import bis.project.validators.LegalPersonDetailsValidator;
import bis.project.validators.ValidationException;

@RestController
public class LegalPersonDetailsController {
	
	@Autowired
	private LegalPersonDetailsServices services;
	
	@RequestMapping(value = "/api/legclients", 
					method = RequestMethod.GET)
	public Set<LegalPersonDetails> getAllClients() {
		Set<LegalPersonDetails> clients = services.getAllClients();
		return clients;
	}
	
	@RequestMapping(value = "/api/legclients/{id}", 
					method = RequestMethod.GET)
	public LegalPersonDetails getClient(@PathVariable("id") Integer id) {
		return services.getClient(id);
	}
	
	@RequestMapping(value = "/api/legclients", 
					method = RequestMethod.POST)
	public LegalPersonDetails addClient(@RequestBody LegalPersonDetails client)   throws ValidationException{
		LegalPersonDetailsValidator.Validate(client);
		return services.addClient(client);
	}
	
	@RequestMapping(value = "/api/legclients/{id}", 
					method = RequestMethod.PUT)
	public LegalPersonDetails updateClient(@PathVariable("id") Integer id, @RequestBody LegalPersonDetails client)   throws ValidationException{
		LegalPersonDetailsValidator.Validate(client);
		client.setId(id);
		return services.updateClient(client);
	}
	
	@RequestMapping(value = "/api/legclients/{id}", 
					method = RequestMethod.DELETE)
	public void deleteClient(@PathVariable("id") Integer id) {
		services.deleteClient(id);
	}
}
