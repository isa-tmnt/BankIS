package bis.project.repositories;

import org.springframework.data.repository.CrudRepository;

import bis.project.model.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
	
}
