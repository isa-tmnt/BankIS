package cagen.project.controllers;

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

import cagen.project.security.Role;
import cagen.project.services.CredentialsServices;
import cagen.project.services.RoleServices;

@RestController
public class RoleController {
	
	@Autowired
	private RoleServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/roles", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<Role>> getAllRoles(@RequestHeader(value="CsrfToken") String csrfToken, 
								 				 @RequestHeader(value="AuthEmail") String authEmail, 
								 				 @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "getAllRoles");
		
		if(isAuthorized) {
			Set<Role> roles = services.getAllRoles();
			
			return new ResponseEntity<Set<Role>>(roles, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<Role>>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/roles/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<Role> getRole(@PathVariable("id") Integer id, 
										@RequestHeader(value="CsrfToken") String csrfToken, 
										@RequestHeader(value="AuthEmail") String authEmail, 
										@CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "getRole");
		
		if(isAuthorized) {
			Role role = services.getRole(id);
			
			if(role != null) {
				return new ResponseEntity<Role>(role, HttpStatus.OK);
			}
			
			return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Role>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/roles", 
					method = RequestMethod.POST)
	public ResponseEntity<Role> addRole(@RequestBody Role role, 
										@RequestHeader(value="CsrfToken") String csrfToken, 
										@RequestHeader(value="AuthEmail") String authEmail, 
										@CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "addRole");
		
		if(isAuthorized) {
			Role body = services.addRole(role);
			
			return new ResponseEntity<Role>(body, HttpStatus.OK);
		}
		
		return new ResponseEntity<Role>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/roles/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<Role> updateRole(@PathVariable("id") Integer id, @RequestBody Role role, 
						   				   @RequestHeader(value="CsrfToken") String csrfToken, 
						   				   @RequestHeader(value="AuthEmail") String authEmail, 
						   				   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "updateRole");
		
		if(isAuthorized) {
			role.setId(id);
			Role body = services.updateRole(role);
			
			return new ResponseEntity<Role>(body, HttpStatus.OK); 
		}
		
		return new ResponseEntity<Role>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/roles/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<Role> deleteRole(@PathVariable("id") Integer id, 
						   				   @RequestHeader(value="CsrfToken") String csrfToken, 
						   				   @RequestHeader(value="AuthEmail") String authEmail, 
						   				   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "deleteRole");
		
		if(isAuthorized) {
			Role role = services.getRole(id);
			
			if(role == null) {
				return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
			}
			
			services.deleteRole(id);
			return new ResponseEntity<Role>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Role>(HttpStatus.UNAUTHORIZED);
	}
}
