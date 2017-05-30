package bis.project.validators;

import bis.project.model.Bank;

public class BankValidator {
	
	
	public static void Validate(Bank bank) throws ValidationException{
	
		ValidationException.ThrowIfNullOrEmpty(bank.getBankCode(), "Bank Code");
		ValidationException.ThrowIfNullOrEmpty(bank.getName(), "Name");	
		ValidationException.ThrowIfNullOrEmpty(bank.getSwiftCode(), "Swift Code");
		ValidationException.ThrowIfNullOrEmpty(bank.getBillingAccount(), "Billing account");
		
		ValidationException.ThrowIfLengthGratherThan(3, bank.getBankCode(), "Bank Code");
		ValidationException.ThrowIfLengthGratherThan(50,bank.getName(), "Name");	
		ValidationException.ThrowIfLengthGratherThan(8,bank.getSwiftCode(), "Swift Code");
		ValidationException.ThrowIfLengthGratherThan(18,bank.getBillingAccount(), "Billing account");
		
	}
	
	
}
