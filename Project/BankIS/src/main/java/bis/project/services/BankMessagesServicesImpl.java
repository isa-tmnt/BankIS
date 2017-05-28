package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.BankMessages;
import bis.project.repositories.BankMessagesRepository;

@Service
public class BankMessagesServicesImpl implements BankMessagesServices {
	
	@Autowired
	private BankMessagesRepository repository;
	
	@Override
	public Set<BankMessages> getBankMessages() {
		Set<BankMessages> bankMessages = new HashSet<BankMessages>();
		for(BankMessages message : repository.findAll()) {
			bankMessages.add(message);
		}
		
		return bankMessages;
	}

	@Override
	public BankMessages getBankMessage(Integer id) {
		BankMessages bankMessage = repository.findOne(id);
		
		if(bankMessage != null) {
			return bankMessage;
		}
		
		return null;
	}

	@Override
	public BankMessages addBankMessage(BankMessages bankMessage) {
		return repository.save(bankMessage);
	}

	@Override
	public BankMessages updateBankMessage(BankMessages bankMessage) {
		return repository.save(bankMessage);
	}

	@Override
	public void deleteBankMessage(Integer id) {
		BankMessages bankMessage = repository.findOne(id);
		
		if(bankMessage != null) {
			repository.delete(id);
		}
	}
}
