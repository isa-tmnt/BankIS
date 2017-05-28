package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.BankAccount;
import bis.project.repositories.BankAccountRepository;

@Service
public class BankAccountServicesImpl implements BankAccountServices {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Override
	public Set<BankAccount> getAllBankAccounts() {
		Set<BankAccount> accounts = new HashSet<BankAccount>();
		for(BankAccount account : bankAccountRepository.findAll()) {
			accounts.add(account);
		}
		return accounts;
	}

	@Override
	public BankAccount getBankAccount(Integer id) {
		BankAccount account = bankAccountRepository.findOne(id);
		
		if(account != null) {
			return account;
		}
		
		return null;
	}

	@Override
	public BankAccount addBankAccount(BankAccount account) {
		return bankAccountRepository.save(account);
	}

	@Override
	public BankAccount updateBankAccount(BankAccount account) {
		return bankAccountRepository.save(account);
	}

	@Override
	public void deleteBankAccount(Integer id) {
		BankAccount account = bankAccountRepository.findOne(id);
		
		if(account != null) {
			bankAccountRepository.delete(id);
		}
	}
}
