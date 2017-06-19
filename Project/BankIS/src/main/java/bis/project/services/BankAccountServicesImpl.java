package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.Bank;
import bis.project.model.BankAccount;
import bis.project.repositories.BankAccountRepository;
import bis.project.repositories.BankRepository;

@Service
public class BankAccountServicesImpl implements BankAccountServices {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private BankRepository repository;

	@Override
	public Set<BankAccount> getAllBankAccounts(Integer bankId) {
		Set<BankAccount> accounts = new HashSet<BankAccount>();
		for(BankAccount account : bankAccountRepository.findAll()) {
			if(account.getBank().getId() == bankId) {
				accounts.add(account);
			}
		}
		return accounts;
	}

	@Override
	public BankAccount getBankAccount(Integer id, Integer bankId) {
		BankAccount account = bankAccountRepository.findOne(id);
		
		if(account != null && account.getBank().getId() == bankId) {
			return account;
		}
		
		return null;
	}

	@Override
	public BankAccount addBankAccount(BankAccount account, Integer bankId) {
		Bank bank = repository.findOne(bankId);
		
		if(bank != null) {
			account.setBank(bank);
			return bankAccountRepository.save(account);
		}
		
		return null;
	}

	@Override
	public BankAccount updateBankAccount(BankAccount account, Integer bankId) {
		Bank bank = repository.findOne(bankId);
		
		if(bank != null) {
			account.setBank(bank);
			return bankAccountRepository.save(account);
		}
		
		return null;
	}

	@Override
	public void deleteBankAccount(Integer id, Integer bankId) {
		BankAccount account = bankAccountRepository.findOne(id);
		
		if(account != null && account.getBank().getId() == bankId) {
			bankAccountRepository.delete(id);
		}
	}
}
