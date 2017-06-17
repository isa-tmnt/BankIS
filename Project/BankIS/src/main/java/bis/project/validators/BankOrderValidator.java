package bis.project.validators;

import bis.project.model.BankOrder;

public class BankOrderValidator {
	public static void Validate(BankOrder bankOrder) throws ValidationException{

		ValidationException.ThrowIfNullOrEmpty(bankOrder.getBankOrderDate(),"Bank order date");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getDirection(),"Direction");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getAmount(),"Amount");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getCurrencyDate(),"Current date");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getDebtor(),"Debtor");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getFirstAccount(),"First account");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getFirstModel(),"First model");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getFirstNumber(),"First number");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getSecondAccount(),"Second account");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getSecondModel(),"Second model");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getSecondNumber(),"Second number");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getOrderDate(),"Order date");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getPurposeOfPayment(),"Purpose of payment");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getRecipient(),"Recipient");
		ValidationException.ThrowIfNullOrEmpty(bankOrder.getDailyAccountBalance(),"Daily account balance");	
		
		ValidationException.ThrowIfLengthGratherThan(255, bankOrder.getDebtor(),"Debtor");
		ValidationException.ThrowIfLengthGratherThan(255, bankOrder.getPurposeOfPayment(),"Purpose of payment");
		ValidationException.ThrowIfLengthGratherThan(255, bankOrder.getRecipient(),"Recipient");
		ValidationException.ThrowIfLengthDifferentThat(18, bankOrder.getFirstAccount(),"First Account");
		ValidationException.ThrowIfLengthGratherThan(2, bankOrder.getFirstModel(),"First Model");
		ValidationException.ThrowIfLengthGratherThan(22, bankOrder.getFirstNumber(),"First Number");
		ValidationException.ThrowIfLengthDifferentThat(18, bankOrder.getSecondAccount(),"Second Account");
		ValidationException.ThrowIfLengthGratherThan(2, bankOrder.getSecondModel(),"Second Model");
		ValidationException.ThrowIfLengthGratherThan(18, bankOrder.getSecondNumber(),"Second Number");
	
	
			
	
	}
}
