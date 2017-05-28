package bis.project.repositories;

import org.springframework.data.repository.CrudRepository;

import bis.project.model.ClientDetails;

public interface ClientDetailsRepository extends CrudRepository<ClientDetails, Integer> {
	
}
