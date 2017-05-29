package bis.project.validators;

import bis.project.model.BankAccount;

public class BankAccountValidator {
	
	public static void Validate(BankAccount bankAccount) throws ValidationException{
		if(bankAccount.getAccountNumber() == null)
			throw new ValidationException("Account number cannot be empty");	
		if(bankAccount.getAccountNumber().length() > 18)
			throw new ValidationException("Account number is max 50 characters");
		
		if(bankAccount.getStatus() == null)
			throw new ValidationException("Status cannot be empty ");		
		if(bankAccount.getStatus().length() != 1)
			throw new ValidationException("Status can be: A - active, D - deactivated");
		
		if(bankAccount.getStartDate() == null)
			throw new ValidationException("Start date cannot be empty");
	
		if(bankAccount.getBank() == null)
			throw new ValidationException("Bank cannot be empty");
		if(bankAccount.getCurrency() == null)
			throw new ValidationException("Currency cannot be empty");
		if(bankAccount.getClient() == null)
			throw new ValidationException("Client cannot be empty");
		}
}
