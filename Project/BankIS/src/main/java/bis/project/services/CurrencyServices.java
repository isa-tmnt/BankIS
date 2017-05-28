package bis.project.services;

import java.util.Set;

import bis.project.model.Currency;

public interface CurrencyServices {
	
	public Set<Currency> getAllCurrency();
	
	public Currency getCurrency(Integer id);
	
	public Currency addCurrency(Currency currency);
	
	public Currency updateCurrency(Currency currency);
	
	public void deleteCurrency(Integer id);
}
