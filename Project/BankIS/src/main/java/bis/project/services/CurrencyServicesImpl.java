package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.Currency;
import bis.project.repositories.CurrencyRepository;

@Service
public class CurrencyServicesImpl implements CurrencyServices {
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Override
	public Set<Currency> getAllCurrency() {
		Set<Currency> currencies = new HashSet<Currency>();
		for(Currency currency : currencyRepository.findAll()) {
			currencies.add(currency);
		}
		return currencies;
	}

	@Override
	public Currency getCurrency(Integer id) {
		Currency currency = currencyRepository.findOne(id);
		
		if(currency != null) {
			return currency;
		}
		
		return null;
	}

	@Override
	public Currency addCurrency(Currency currency) {
		return currencyRepository.save(currency);
	}

	@Override
	public Currency updateCurrency(Currency currency) {
		return currencyRepository.save(currency);
	}

	@Override
	public void deleteCurrency(Integer id) {
		Currency currency = currencyRepository.findOne(id);
		
		if(currency != null) {
			currencyRepository.delete(id);
		}
	}
}
