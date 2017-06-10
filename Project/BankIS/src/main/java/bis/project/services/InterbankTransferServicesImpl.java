package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.DailyAccountBalance;
import bis.project.model.InterbankTransfer;
import bis.project.model.LegalPersonDetails;
import bis.project.repositories.DailyAccountBalanceRepository;
import bis.project.repositories.InterbankTransferRepository;

@Service
public class InterbankTransferServicesImpl implements InterbankTransferServices {

	@Autowired
	private InterbankTransferRepository repository;
	
	@Override
	public Set<InterbankTransfer> getAllInterbankTrasfers() {
		Set<InterbankTransfer> clients = new HashSet<InterbankTransfer>();
		for(InterbankTransfer client : repository.findAll()) {
			clients.add(client);
		}
		
		return clients;
	}

	@Override
	public InterbankTransfer getInterbankTransfer(Integer id) {
		return repository.findOne(id);
	}

	@Override
	public InterbankTransfer addInterbankTransfer(InterbankTransfer interbankTransfer) {
		return repository.save(interbankTransfer);
	}

	@Override
	public InterbankTransfer updateInterbankTransfer(InterbankTransfer interbankTransfer) {
		return repository.save(interbankTransfer);
	}

	@Override
	public void deleteInterbankTrasfer(Integer id) {
		InterbankTransfer client = repository.findOne(id);
		
		if(client != null) {
			repository.delete(id);
		}
	}

}
