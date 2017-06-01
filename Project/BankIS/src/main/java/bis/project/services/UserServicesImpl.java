package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.repositories.UserRepository;
import bis.project.security.User;

@Service
public class UserServicesImpl implements UserServices {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public Set<User> getAllUsers() {
		Set<User> users = new HashSet<User>();
		for(User user : repository.findAll()) {
			users.add(user);
		}
		
		return users;
	}

	@Override
	public User getUser(Integer id) {
		User user = repository.findOne(id);
		
		if(user != null) {
			return user;
		}
		
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = repository.findByEmail(email);
		
		if(user != null) {
			return user;
		}
		
		return null;
	}

	@Override
	public User addUser(User user) {
		return repository.save(user);
	}

	@Override
	public User updateUser(User user) {
		return repository.save(user);
	}

	@Override
	public void deleteUser(Integer id) {
		User user = repository.findOne(id);
		
		if(user != null) {
			repository.delete(id);
		}
	}
}
