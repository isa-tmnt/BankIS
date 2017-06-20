package cagen.project.repositories;

import org.springframework.data.repository.CrudRepository;

import cagen.project.security.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
