package cagen.project.repositories;

import org.springframework.data.repository.CrudRepository;

import cagen.project.security.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	public User findByEmail(String email);
}
