package bis.project.validators;

import bis.project.model.Currency;

public class CurrencyValidator {
	public static void Validate(Currency currency) throws ValidationException{

		ValidationException.ThrowIfNullOrEmpty(currency.getCurrencyCode(),"Currency code");
		ValidationException.ThrowIfNullOrEmpty(currency.getName(),"Name");

		ValidationException.ThrowIfLengthGratherThan(3, currency.getCurrencyCode(), "Currency code");
		ValidationException.ThrowIfLengthGratherThan(30, currency.getName(), "Name");
		
	}
}
