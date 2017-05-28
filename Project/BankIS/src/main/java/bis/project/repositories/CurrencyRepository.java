package bis.project.repositories;

import org.springframework.data.repository.CrudRepository;

import bis.project.model.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
	
}
