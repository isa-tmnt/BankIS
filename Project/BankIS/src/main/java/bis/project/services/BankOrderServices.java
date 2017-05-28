package bis.project.services;

import java.util.Set;

import bis.project.model.BankOrder;

public interface BankOrderServices {
	
	public Set<BankOrder> getBankOrders();
	
	public BankOrder getBankOrder(Integer id);
	
	public BankOrder addBankOrder(BankOrder bankOrder);
	
	public BankOrder updateBankOrder(BankOrder bankOrder);
	
	public void deleteBankOrder(Integer id);
}
