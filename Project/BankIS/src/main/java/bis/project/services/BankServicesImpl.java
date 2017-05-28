package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.Bank;
import bis.project.repositories.BankRepository;

@Service
public class BankServicesImpl implements BankServices {

	@Autowired
	private BankRepository bankRepository;
	
	@Override
	public Set<Bank> getAllBanks() {
		Set<Bank> banks = new HashSet<Bank>();
		for(Bank bank : bankRepository.findAll()) {
			banks.add(bank);
		}
		
		return banks;
	}

	@Override
	public Bank getBank(Integer id) {
		Bank bank = bankRepository.findOne(id);
		
		if(bank != null) {
			return bank;
		}
		
		return null;
	}

	@Override
	public Bank addBank(Bank bank) {
		return bankRepository.save(bank);
	}

	@Override
	public Bank updateBank(Bank bank) {
		return bankRepository.save(bank);
	}

	@Override
	public void deleteBank(Integer id) {
		Bank bank = bankRepository.findOne(id);
		
		if(bank != null) {
			bankRepository.delete(id);
		}
	}
}
