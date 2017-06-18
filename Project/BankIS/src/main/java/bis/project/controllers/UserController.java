package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.security.PasswordDTO;
import bis.project.security.UserDTO;
import bis.project.security.UserResponse;
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
	public ResponseEntity<Set<UserResponse>> getAllUsers(@RequestHeader(value="CsrfToken") String csrfToken, 
														 @RequestHeader(value="AuthEmail") String authEmail, 
														 @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "GetAllUsers");
		
		if(isAuthorized) {
			Set<UserResponse> users = services.getAllUsers();
			
			return new ResponseEntity<Set<UserResponse>>(users, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<UserResponse>>(HttpStatus.UNAUTHORIZED);
	}
	
	/*@RequestMapping(value = "/api/users/get/{id}", 
					method = RequestMethod.GET)
	public UserDTO getUser(@PathVariable("id") Integer id) {
		return services.getUser(id);
	}*/
	
	@RequestMapping(value = "/api/users/{email}", 
					method = RequestMethod.GET)
	//api/users/example@gmail.com.
	public ResponseEntity<UserResponse> getUserByEmail(@PathVariable("email") String email, 
												  @RequestHeader(value="CsrfToken") String csrfToken, 
												  @RequestHeader(value="AuthEmail") String authEmail, 
												  @CookieValue("jwt") String jwt) {
		
		//I can't get .com unless i end string with .
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "GetUserByEmail");
		
		if(isAuthorized) {
			UserResponse user = services.getUserByEmail(email);
			
			if(user == null) {
				return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<UserResponse>(user, HttpStatus.OK);
		}
		
		return new ResponseEntity<UserResponse>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/users", 
					method = RequestMethod.POST)
	public ResponseEntity<UserResponse> addUser(@RequestBody UserDTO user, 
										   		@RequestHeader(value="CsrfToken") String csrfToken, 
										   		@RequestHeader(value="AuthEmail") String authEmail, 
										   		@CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "AddUser");
		
		if(isAuthorized) {
			//UserValidator.Validate(user);
			UserResponse newUser = services.addUser(user);
			
			if(newUser == null) {
				return new ResponseEntity<UserResponse>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			return new ResponseEntity<UserResponse>(newUser, HttpStatus.OK);
		}
		
		return new ResponseEntity<UserResponse>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/users", 
					method = RequestMethod.PUT)
	public ResponseEntity<UserResponse> updateUser(@RequestBody PasswordDTO dto, 
											  	   @RequestHeader(value="CsrfToken") String csrfToken, 
											  	   @RequestHeader(value="AuthEmail") String authEmail, 
											  	   @CookieValue("jwt") String jwt) throws ValidationException {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "UpdateUser");
		
		if(isAuthorized) {
			//UserValidator.Validate(user);
			UserResponse udto = services.getUserByEmail(authEmail);
			
			if(udto == null) {
				return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND); 
			}
			
			UserResponse updatedUser = services.updateUser(authEmail, dto);
			
			return new ResponseEntity<UserResponse>(updatedUser, HttpStatus.OK);
		}
		
		return new ResponseEntity<UserResponse>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/users/{email}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<UserResponse> deleteUser(@PathVariable("email") String email, 
											  	   @RequestHeader(value="CsrfToken") String csrfToken, 
											  	   @RequestHeader(value="AuthEmail") String authEmail, 
											  	   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "DeleteUser");
		
		if(isAuthorized) {
			UserResponse udto = services.getUserByEmail(email);
			
			if(udto == null) {
				return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND); 
			}
			
			services.deleteUser(email);
			return new ResponseEntity<UserResponse>(HttpStatus.OK);
		}
		
		return new ResponseEntity<UserResponse>(HttpStatus.UNAUTHORIZED);
	}
}
