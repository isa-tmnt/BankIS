package bis.project.services;

import java.util.Set;

import bis.project.model.BankMessages;

public interface BankMessagesServices {
	
	public Set<BankMessages> getBankMessages();
	
	public BankMessages getBankMessage(Integer id);
	
	public BankMessages addBankMessage(BankMessages bankMessage);
	
	public BankMessages updateBankMessage(BankMessages bankMessage);
	
	public void deleteBankMessage(Integer id);
}
