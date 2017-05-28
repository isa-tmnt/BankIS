package bis.project.services;

import java.util.Set;

import bis.project.model.ClosingAccount;

public interface ClosingAccountServices {
	
	public Set<ClosingAccount> getAllClosingAccounts();
	
	public ClosingAccount getClosingAccount(Integer id);
	
	public ClosingAccount addClosingAccount(ClosingAccount closingAccount);
	
	public ClosingAccount updateClosingAccount(ClosingAccount closingAccount);
	
	public void deleteClosingAccount(Integer id);
}
