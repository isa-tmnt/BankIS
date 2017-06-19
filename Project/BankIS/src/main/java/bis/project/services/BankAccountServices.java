package bis.project.services;

import java.util.Set;

import bis.project.model.BankAccount;

public interface BankAccountServices {
	
	public Set<BankAccount> getAllBankAccounts(Integer bankId);
	
	public BankAccount getBankAccount(Integer id, Integer bankId);
	
	public BankAccount addBankAccount(BankAccount account, Integer bankId);
	
	public BankAccount updateBankAccount(BankAccount account, Integer bankId);
	
	public void deleteBankAccount(Integer id, Integer bankId);
}
