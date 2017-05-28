package bis.project.services;

import java.util.Set;

import bis.project.model.BankAccount;

public interface BankAccountServices {
	
	public Set<BankAccount> getAllBankAccounts();
	
	public BankAccount getBankAccount(Integer id);
	
	public BankAccount addBankAccount(BankAccount account);
	
	public BankAccount updateBankAccount(BankAccount account);
	
	public void deleteBankAccount(Integer id);
}
