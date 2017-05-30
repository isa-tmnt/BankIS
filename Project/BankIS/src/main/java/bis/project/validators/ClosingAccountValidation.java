package bis.project.validators;

import bis.project.model.ClosingAccount;

public class ClosingAccountValidation {
	public static void Validate(ClosingAccount closingAccount) throws ValidationException{

		ValidationException.ThrowIfNullOrEmpty(closingAccount.getSwitchToAnAccount(),"Switch To An Account");
		ValidationException.ThrowIfNullOrEmpty(closingAccount.getEndDate(),"End date");
		ValidationException.ThrowIfNullOrEmpty(closingAccount.getAccount(),"Account");

		ValidationException.ThrowIfLengthGratherThan(18, closingAccount.getSwitchToAnAccount(), "Switch To An Account");
	
		
	}
}
