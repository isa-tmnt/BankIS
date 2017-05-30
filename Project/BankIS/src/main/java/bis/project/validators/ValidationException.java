package bis.project.validators;

public class ValidationException extends Exception {
	
	public ValidationException(){
		super();
	}
	public ValidationException(String message){
		super(message);
	}
	
	
	public static void ThrowIfNullOrEmpty(Object obj,String fieldLabel) throws ValidationException{
		if(obj == null || (obj instanceof String && ((String)obj).isEmpty()))
			throw new ValidationException(fieldLabel + " cannot be empty");
	}
	
	public static void ThrowIfLengthGratherThan(int length, String str,String fieldLabel) throws ValidationException{
		if(str != null && str.length() > length)
			throw new ValidationException(fieldLabel + " is max " + length + " characters");
	}
	
	public static void ThrowIfLengthGratherThan(int length, int number,String fieldLabel) throws ValidationException{
		if(String.valueOf(number).length() > length)
			throw new ValidationException(fieldLabel + " cannot be greather than  " + length);
	}
	
	public static void ThrowIfLengthIsNotBetween(int min, int max, String str, String fieldLabel)throws ValidationException{
		if(str == null) return;
		if(str.length() < min || str.length() > max)
			throw new ValidationException(fieldLabel + " have to be between " + min + " and " + max);

	}
	
	public static void ThrowIfNotNullAndLengthIsNotBetween(int min, int max, String str, String fieldLabel)throws ValidationException{
		if(str == null) return;
		if(str.length() < min || str.length() > max)
			throw new ValidationException(fieldLabel + " have to be between " + min + " and " + max + " or empty");

	}
}
