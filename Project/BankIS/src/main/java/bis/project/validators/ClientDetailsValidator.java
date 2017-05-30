package bis.project.validators;

import bis.project.model.ClientDetails;

public class ClientDetailsValidator {
	public static void Validate(ClientDetails clientDetails) throws ValidationException{

		ValidationException.ThrowIfNullOrEmpty(clientDetails.getJmbg(),"JMGB");
		ValidationException.ThrowIfNullOrEmpty(clientDetails.getFirstName(),"First name");
		ValidationException.ThrowIfNullOrEmpty(clientDetails.getLastName(),"Last name");
	
		ValidationException.ThrowIfLengthGratherThan(13, clientDetails.getJmbg(), "JMBG");
		ValidationException.ThrowIfLengthGratherThan(50, clientDetails.getFirstName(), "First Name");
		ValidationException.ThrowIfLengthGratherThan(50, clientDetails.getLastName(), "Last Name");
		ValidationException.ThrowIfLengthGratherThan(80, clientDetails.getAddress(), "Address");	
		ValidationException.ThrowIfLengthGratherThan(80, clientDetails.getEmail(), "Email");
		ValidationException.ThrowIfLengthGratherThan(30, clientDetails.getPhoneNumber(), "Phone number");
		
	}
}
