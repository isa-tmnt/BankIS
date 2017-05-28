package bis.project.repositories;

import org.springframework.data.repository.CrudRepository;

import bis.project.model.BankOrder;

public interface BankOrderRepository extends CrudRepository<BankOrder, Integer> {

}
