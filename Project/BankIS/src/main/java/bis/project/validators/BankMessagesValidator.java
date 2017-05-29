package bis.project.validators;

import bis.project.model.BankMessages;

public class BankMessagesValidator {
	public static void Validate(BankMessages bankAccount) throws ValidationException{
		if(bankAccount.getMessageCode() == null)
			throw new ValidationException("Message code cannot be empty");	
		if(bankAccount.getMessageCode().length() > 20)
			throw new ValidationException("Message code is max 20 characters");
		
		if(bankAccount.getDescription() != null &&bankAccount.getDescription().length() > 255)
			throw new ValidationException("Description is max 255 characters");
	}
}
