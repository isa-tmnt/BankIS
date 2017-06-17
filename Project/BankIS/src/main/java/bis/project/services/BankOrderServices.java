package bis.project.services;

import java.util.Set;

import bis.project.model.BankOrder;
import bis.project.validators.ValidationException;

public interface BankOrderServices {
	
	public Set<BankOrder> getBankOrders();
	
	public BankOrder getBankOrder(Integer id);
	
	public BankOrder addBankOrder(BankOrder bankOrder) throws ValidationException;
	
	public BankOrder updateBankOrder(BankOrder bankOrder); //TODO remove this
	
	public void deleteBankOrder(Integer id);

	public void processMt103FromCentralBank(BankOrder order) throws ValidationException;
}
