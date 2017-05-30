package bis.project.validators;

import bis.project.model.BankMessages;

public class BankMessagesValidator {
	public static void Validate(BankMessages bankMessage) throws ValidationException{
		
		ValidationException.ThrowIfNullOrEmpty(bankMessage.getMessageCode(), "Message code");
		
		ValidationException.ThrowIfLengthGratherThan(20, bankMessage.getMessageCode(), "Message code");
		ValidationException.ThrowIfLengthGratherThan(225, bankMessage.getDescription(), "Description");
			
	}
}
