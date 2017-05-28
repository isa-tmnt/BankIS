package bis.project.repositories;

import org.springframework.data.repository.CrudRepository;

import bis.project.model.Bank;

public interface BankRepository extends CrudRepository<Bank, Integer> {
	
}
