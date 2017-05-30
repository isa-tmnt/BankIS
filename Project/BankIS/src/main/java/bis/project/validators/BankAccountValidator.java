package bis.project.validators;

import bis.project.model.BankAccount;

public class BankAccountValidator {
	
	public static void Validate(BankAccount bankAccount) throws ValidationException{
		
		ValidationException.ThrowIfNullOrEmpty(bankAccount.getAccountNumber(),"Account Number");
		ValidationException.ThrowIfNullOrEmpty(bankAccount.getStatus(),"Status");
		ValidationException.ThrowIfNullOrEmpty(bankAccount.getStartDate(),"Start date");	
		
		ValidationException.ThrowIfNullOrEmpty(bankAccount.getBank(),"Bank");
		ValidationException.ThrowIfNullOrEmpty(bankAccount.getCurrency(),"Currency");
		ValidationException.ThrowIfNullOrEmpty(bankAccount.getClient(),"Client");
		
		
		ValidationException.ThrowIfLengthGratherThan(18, bankAccount.getAccountNumber(), "Account Number");
		ValidationException.ThrowIfLengthGratherThan(1, bankAccount.getStatus(), "Status");
	}
}
