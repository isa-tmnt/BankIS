package bis.project.services;

import java.util.Set;

import bis.project.model.LegalPersonDetails;

public interface LegalPersonDetailsServices {
	
	public Set<LegalPersonDetails> getAllClients();
	
	public LegalPersonDetails getClient(Integer id);
	
	public LegalPersonDetails addClient(LegalPersonDetails client);
	
	public LegalPersonDetails updateClient(LegalPersonDetails client);
	
	public void deleteClient(Integer id);
}
