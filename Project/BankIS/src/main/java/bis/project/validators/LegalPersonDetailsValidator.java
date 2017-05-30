package bis.project.validators;

import bis.project.model.LegalPersonDetails;

public class LegalPersonDetailsValidator {
	public static void Validate(LegalPersonDetails clientDetails) throws ValidationException{

		ClientDetailsValidator.Validate(clientDetails);
		
		ValidationException.ThrowIfLengthGratherThan(9, clientDetails.getPib(), "PIB");
		ValidationException.ThrowIfLengthGratherThan(30, clientDetails.getFax(), "Fax");
		ValidationException.ThrowIfLengthGratherThan(255, clientDetails.getWebPage(), "Web page");
	}
}
