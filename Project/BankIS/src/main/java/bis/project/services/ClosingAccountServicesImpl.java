package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.ClosingAccount;
import bis.project.repositories.ClosingAccountRepository;

@Service
public class ClosingAccountServicesImpl implements ClosingAccountServices {
	
	@Autowired
	private ClosingAccountRepository closingAccountRepository;
	
	@Override
	public Set<ClosingAccount> getAllClosingAccounts() {
		Set<ClosingAccount> accounts = new HashSet<ClosingAccount>();
		for(ClosingAccount account : closingAccountRepository.findAll()) {
			accounts.add(account);
		}
		
		return accounts;
	}

	@Override
	public ClosingAccount getClosingAccount(Integer id) {
		ClosingAccount closingAccount = closingAccountRepository.findOne(id);
		
		if(closingAccount != null) {
			return closingAccount;
		}
		
		return null;
	}

	@Override
	public ClosingAccount addClosingAccount(ClosingAccount closingAccount) {
		return closingAccountRepository.save(closingAccount);
	}

	@Override
	public ClosingAccount updateClosingAccount(ClosingAccount closingAccount) {
		return closingAccountRepository.save(closingAccount);
	}

	@Override
	public void deleteClosingAccount(Integer id) {
		ClosingAccount closingAccount = closingAccountRepository.findOne(id);
		
		if(closingAccount != null) {
			closingAccountRepository.delete(id);
		}
	}
}
