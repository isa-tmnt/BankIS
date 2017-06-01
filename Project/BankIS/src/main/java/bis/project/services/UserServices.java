package bis.project.services;

import java.util.Set;

import bis.project.security.User;

public interface UserServices {
	
	public Set<User> getAllUsers();
	
	public User getUser(Integer id);
	
	public User getUserByEmail(String email);
	
	public User addUser(User user);
	
	public User updateUser(User user);
	
	public void deleteUser(Integer id);
}
