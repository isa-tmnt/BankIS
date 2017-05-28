package bis.project.repositories;

import org.springframework.data.repository.CrudRepository;

import bis.project.model.BankMessages;

public interface BankMessagesRepository extends CrudRepository<BankMessages, Integer> {

}
