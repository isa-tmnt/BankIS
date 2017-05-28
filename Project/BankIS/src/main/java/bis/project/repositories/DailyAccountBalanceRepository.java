package bis.project.repositories;

import org.springframework.data.repository.CrudRepository;

import bis.project.model.DailyAccountBalance;

public interface DailyAccountBalanceRepository extends CrudRepository<DailyAccountBalance, Integer> {
	
	
}
