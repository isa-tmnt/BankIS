package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.security.User;
import bis.project.services.UserServices;

@RestController
public class UserController {
	
	@Autowired
	private UserServices services;
	
	@RequestMapping(value = "/api/users", 
					method = RequestMethod.GET)
	public Set<User> getAllUsers() {
		Set<User> users = services.getAllUsers();
		
		return users;
	}
	
	@RequestMapping(value = "/api/users/{id}", 
					method = RequestMethod.GET)
	public User getUser(@PathVariable("id") Integer id) {
		return services.getUser(id);
	}
	
	@RequestMapping(value = "/api/users/get/{email}", 
					method = RequestMethod.GET)
	public User getUserByEmail(@PathVariable("email") String email) {
		return services.getUserByEmail(email);
	}
	
	@RequestMapping(value = "/api/users", 
					method = RequestMethod.POST)
	public User addUser(@RequestBody User user) {
		return services.addUser(user);
	}
	
	@RequestMapping(value = "/api/users/{id}", 
					method = RequestMethod.PUT)
	public User updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
		user.setId(id);
		return services.updateUser(user);
	}
	
	@RequestMapping(value = "/api/users/{id}", 
					method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable("id") Integer id) {
		services.deleteUser(id);
	}
}
