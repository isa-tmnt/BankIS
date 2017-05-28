package bis.project.services;

import java.util.Set;

import bis.project.model.ClientDetails;

public interface ClientDetailsServices {
	
	public Set<ClientDetails> getAllClients();
	
	public ClientDetails getClient(Integer id);
	
	public ClientDetails addClient(ClientDetails client);
	
	public ClientDetails updateClient(ClientDetails client);
	
	public void deleteClient(Integer id);
}
