package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.LegalPersonDetails;
import bis.project.repositories.LegalPersonDetailsRepository;

@Service
public class LegalPersonDetailsServicesImpl implements LegalPersonDetailsServices {
	
	@Autowired
	private LegalPersonDetailsRepository repository;
	
	@Override
	public Set<LegalPersonDetails> getAllClients() {
		Set<LegalPersonDetails> clients = new HashSet<LegalPersonDetails>();
		for(LegalPersonDetails client : repository.findAll()) {
			clients.add(client);
		}
		
		return clients;
	}

	@Override
	public LegalPersonDetails getClient(Integer id) {
		LegalPersonDetails client = repository.findOne(id);
		
		if(client != null) {
			return client;
		}
		
		return null;
	}

	@Override
	public LegalPersonDetails addClient(LegalPersonDetails client) {
		return repository.save(client);
	}

	@Override
	public LegalPersonDetails updateClient(LegalPersonDetails client) {
		return repository.save(client);
	}

	@Override
	public void deleteClient(Integer id) {
		LegalPersonDetails client = repository.findOne(id);
		
		if(client != null) {
			repository.delete(id);
		}
	}
}
