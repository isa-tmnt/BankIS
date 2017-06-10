package bis.project.validators;

import bis.project.security.User;

public class UserValidator {
	public static void Validate(User user) throws ValidationException{

		ValidationException.ThrowIfNullOrEmpty(user.getFirstName(),"First name");
		ValidationException.ThrowIfNullOrEmpty(user.getLastName(),"Last name");
		ValidationException.ThrowIfNullOrEmpty(user.getPassword(),"Password");
		ValidationException.ThrowIfNullOrEmpty(user.getEmail(),"Email");
	
		
		ValidationException.ThrowIfLengthGratherThan(80, user.getFirstName(), "First name");
		ValidationException.ThrowIfLengthGratherThan(80, user.getLastName(), "Last name");
		ValidationException.ThrowIfLengthGratherThan(120, user.getEmail(), "Email");
		ValidationException.ThrowIfLengthGratherThan(20, user.getEmail(), "Password");
	}
}
