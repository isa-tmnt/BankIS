package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.security.UserDTO;
import bis.project.services.CredentialsServices;
import bis.project.services.UserServices;
import bis.project.validators.ValidationException;

@RestController
public class UserController {
	
	@Autowired
	private UserServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/users", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<UserDTO>> getAllUsers(@RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "GetAllUsers");
		
		if(isAuthorized) {
			Set<UserDTO> users = services.getAllUsers();
			
			return new ResponseEntity<Set<UserDTO>>(users, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<UserDTO>>(HttpStatus.UNAUTHORIZED);
	}
	
	/*@RequestMapping(value = "/api/users/get/{id}", 
					method = RequestMethod.GET)
	public UserDTO getUser(@PathVariable("id") Integer id) {
		return services.getUser(id);
	}*/
	
	@RequestMapping(value = "/api/users/{email}", 
					method = RequestMethod.GET)
	//api/users/example@gmail.com.
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable("email") String email, @RequestHeader(value="Authorization") String basicAuth) {
		//I can't get .com unless i end string with .
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "GetUserByEmail");
		
		if(isAuthorized) {
			UserDTO user = services.getUserByEmail(email);
			
			if(user == null) {
				return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
		}
		
		return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/users", 
					method = RequestMethod.POST)
	public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO user, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "AddUser");
		
		if(isAuthorized) {
			//UserValidator.Validate(user);
			UserDTO newUser = services.addUser(user);
			
			if(newUser == null) {
				return new ResponseEntity<UserDTO>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			return new ResponseEntity<UserDTO>(newUser, HttpStatus.OK);
		}
		
		return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/users/{email}", 
					method = RequestMethod.PUT)
	public ResponseEntity<UserDTO> updateUser(@PathVariable("email") String email, @RequestBody UserDTO user, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "UpdateUser");
		
		if(isAuthorized) {
			//UserValidator.Validate(user);
			UserDTO udto = services.getUserByEmail(email);
			
			if(udto == null) {
				return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND); 
			}
			
			UserDTO updatedUser = services.updateUser(email, user);
			
			return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
		}
		
		return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/users/{email}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<UserDTO> deleteUser(@PathVariable("email") String email, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "DeleteUser");
		
		if(isAuthorized) {
			UserDTO udto = services.getUserByEmail(email);
			
			if(udto == null) {
				return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND); 
			}
			
			services.deleteUser(email);
			return new ResponseEntity<UserDTO>(HttpStatus.OK);
		}
		
		return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
	}
}
