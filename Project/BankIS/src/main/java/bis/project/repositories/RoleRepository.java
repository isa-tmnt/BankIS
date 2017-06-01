package bis.project.repositories;

import org.springframework.data.repository.CrudRepository;

import bis.project.security.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
