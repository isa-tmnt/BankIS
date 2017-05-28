package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.Currency;
import bis.project.services.CurrencyServices;

@RestController
public class CurrencyController {
	
	@Autowired
	private CurrencyServices currencyServices;
	
	@RequestMapping(value = "/api/currencies", 
					method = RequestMethod.GET)
	public Set<Currency> getAllCurrencies() {
		Set<Currency> currencies = currencyServices.getAllCurrency();
		return currencies;
	}
	
	@RequestMapping(value = "/api/currencies/{id}", 
					method = RequestMethod.GET)
	public Currency getCurrency(@PathVariable("id") Integer id) {
		return currencyServices.getCurrency(id);
	}
	
	@RequestMapping(value = "/api/currencies", 
					method = RequestMethod.POST)
	public Currency addCurrency(@RequestBody Currency currency) {
		return currencyServices.addCurrency(currency);
	}
	
	@RequestMapping(value = "/api/currencies/{id}", 
					method = RequestMethod.PUT)
	public Currency updateCurrency(@PathVariable("id") Integer id, @RequestBody Currency currency) {
		currency.setId(id);
		return currencyServices.updateCurrency(currency);
	}
	
	@RequestMapping(value = "/api/currencies/{id}", 
					method = RequestMethod.DELETE)
	public void deleteCurrency(@PathVariable("id") Integer id) {
		currencyServices.deleteCurrency(id);
	}
}
