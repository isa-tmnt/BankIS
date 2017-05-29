package bis.project.validators;

import bis.project.model.Bank;

public class BankValidator {
	
	
	public static void Validate(Bank bank) throws ValidationException{
		if(bank.getBankCode() > 999)
			throw new ValidationException("Bank code is max 3 digits");
		if(bank.getName() == null)
			throw new ValidationException("Bank name cannot be empty");	
		if(bank.getName().length() > 50)
			throw new ValidationException("Bank name is max 50 characters");
		if(bank.getSwiftCode() == null)
			throw new ValidationException("Swift code cannot be empty ");		
		if(bank.getSwiftCode().length() > 8)
			throw new ValidationException("Swift code is max 8 characters");
		if(bank.getBillingAccount() == null)
			throw new ValidationException("Billing account cannot be empty");		
		if(bank.getBillingAccount().length() > 18)
			throw new ValidationException("Billing account is max 18 characters");
	}
	
}
