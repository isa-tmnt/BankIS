package bis.project.services;

import java.util.Set;

import bis.project.model.Bank;

public interface BankServices {
	
	public Set<Bank> getAllBanks();
	
	public Bank getBank(Integer id);
	
	public Bank addBank(Bank bank);
	
	public Bank updateBank(Bank bank);
	
	public void deleteBank(Integer id);
}
