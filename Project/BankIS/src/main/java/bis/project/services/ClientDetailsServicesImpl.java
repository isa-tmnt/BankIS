package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.ClientDetails;
import bis.project.repositories.ClientDetailsRepository;

@Service
public class ClientDetailsServicesImpl implements ClientDetailsServices {
	
	@Autowired
	private ClientDetailsRepository repository;
	
	@Override
	public Set<ClientDetails> getAllClients() {
		Set<ClientDetails> clients = new HashSet<ClientDetails>();
		for(ClientDetails client : repository.findAll()) {
			clients.add(client);
		}
		
		return clients;
	}

	@Override
	public ClientDetails getClient(Integer id) {
		ClientDetails client = repository.findOne(id);
		
		if(client != null) {
			return client;
		}
		
		return null;
	}

	@Override
	public ClientDetails addClient(ClientDetails client) {
		return repository.save(client);
	}

	@Override
	public ClientDetails updateClient(ClientDetails client) {
		return repository.save(client);
	}

	@Override
	public void deleteClient(Integer id) {
		ClientDetails client = repository.findOne(id);
		
		if(client != null) {
			repository.delete(id);
		}
	}
}
