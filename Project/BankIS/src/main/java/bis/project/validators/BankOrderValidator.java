package bis.project.validators;

import bis.project.model.BankOrder;

public class BankOrderValidator {
	public static void Validate(BankOrder bankOrder) throws ValidationException{

		ValidationException.ThrowIfNullOrEmpty(bankOrder.getBankOrderDate(),"Bank order date");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getDirection(),"Direction");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getAmount(),"Amount");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.isUrgently(),"Urgently");
	
		ValidationException.ThrowIfLengthGratherThan(255, bankOrder.getDebtor(),"Debtor");
		ValidationException.ThrowIfLengthGratherThan(255, bankOrder.getPurposeOfPayment(),"Purpose of payment");
		ValidationException.ThrowIfLengthGratherThan(255, bankOrder.getRecipient(),"Recipient");
		ValidationException.ThrowIfLengthGratherThan(18, bankOrder.getFirstAccount(),"First Account");
		ValidationException.ThrowIfLengthGratherThan(2, bankOrder.getFirstModel(),"First Model");
		ValidationException.ThrowIfLengthGratherThan(22, bankOrder.getFirstNumber(),"First Number");
		ValidationException.ThrowIfLengthGratherThan(18, bankOrder.getSecondNumber(),"Second Number");
	
	
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getDailyAccountBalance(),"Daily account balance");		
	
	}
}
