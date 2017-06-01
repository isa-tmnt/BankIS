package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.ClientDetails;
import bis.project.services.ClientDetailsServices;

@RestController
public class ClientDetailsController {
	
	@Autowired
	private ClientDetailsServices services;
	
	@RequestMapping(value = "/api/clients", 
					method = RequestMethod.GET)
	public Set<ClientDetails> getAllClients() {
		Set<ClientDetails> clients = services.getAllClients();
		return clients;
	}
	
	@RequestMapping(value = "/api/clients/{id}", 
					method = RequestMethod.GET)
	public ClientDetails getClient(@PathVariable("id") Integer id) {
		return services.getClient(id);
	}
	
	@RequestMapping(value = "/api/clients", 
					method = RequestMethod.POST)
	public ClientDetails addClient(@RequestBody ClientDetails client) {
		return services.addClient(client);
	}
	
	@RequestMapping(value = "/api/clients/{id}", 
					method = RequestMethod.PUT)
	public ClientDetails updateClient(@PathVariable("id") Integer id, @RequestBody ClientDetails client) {
		client.setId(id);
		return services.updateClient(client);
	}
	
	@RequestMapping(value = "/api/clients/{id}", 
					method = RequestMethod.DELETE)
	public void deleteClient(@PathVariable("id") Integer id) {
		services.deleteClient(id);
	}
}
