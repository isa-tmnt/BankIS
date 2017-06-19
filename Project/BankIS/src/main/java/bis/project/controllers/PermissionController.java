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

import bis.project.security.Permission;
import bis.project.services.CredentialsServices;
import bis.project.services.PermissionServices;

@RestController
public class PermissionController {
	
	@Autowired
	private PermissionServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/permissions", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<Permission>> getAllPermissions(@RequestHeader(value="CsrfToken") String csrfToken, 
															 @RequestHeader(value="AuthEmail") String authEmail, 
															 @RequestHeader(value="BankId") Integer bankId, 
															 @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "getAllPermissions");
		
		if(isAuthorized) {
			Set<Permission> permissions = services.getAllPermissions();
			
			return new ResponseEntity<Set<Permission>>(permissions, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<Permission>>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/permissions/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<Permission> getPermission(@PathVariable("id") Integer id, 
													@RequestHeader(value="CsrfToken") String csrfToken, 
													@RequestHeader(value="AuthEmail") String authEmail, 
													@RequestHeader(value="BankId") Integer bankId, 
													@CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "getPermission");
		
		if(isAuthorized) {
			Permission body = services.getPermission(id);
			
			if(body != null) {
				return new ResponseEntity<Permission>(body, HttpStatus.OK);
			}
			
			return new ResponseEntity<Permission>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Permission>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/permissions", 
					method = RequestMethod.POST)
	public ResponseEntity<Permission> addPermission(@RequestBody Permission permission, 
													@RequestHeader(value="CsrfToken") String csrfToken, 
													@RequestHeader(value="AuthEmail") String authEmail, 
													@RequestHeader(value="BankId") Integer bankId, 
													@CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "addPermission");
		
		if(isAuthorized) {
			Permission body = services.addPermission(permission);
			
			return new ResponseEntity<Permission>(body, HttpStatus.OK);
		}
		
		return new ResponseEntity<Permission>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/permissions/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<Permission> updatePermission(@PathVariable("id") Integer id, @RequestBody Permission permission, 
													   @RequestHeader(value="CsrfToken") String csrfToken, 
													   @RequestHeader(value="AuthEmail") String authEmail, 
													   @RequestHeader(value="BankId") Integer bankId, 
													   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "updatePermission");
		
		if(isAuthorized) {
			permission.setId(id);
			Permission body = services.updatePermission(permission);
			
			return new ResponseEntity<Permission>(body, HttpStatus.OK);
		}
		
		return new ResponseEntity<Permission>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/permissions/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<Permission> deletePermission(@PathVariable("id") Integer id, 
													   @RequestHeader(value="CsrfToken") String csrfToken, 
													   @RequestHeader(value="AuthEmail") String authEmail, 
													   @RequestHeader(value="BankId") Integer bankId, 
													   @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, bankId, "deletePermission");
		
		if(isAuthorized) {
			Permission permission = services.getPermission(id);
			
			if(permission == null) {
				return new ResponseEntity<Permission>(HttpStatus.NOT_FOUND);
			}
			
			services.deletePermission(id);
			return new ResponseEntity<Permission>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Permission>(HttpStatus.UNAUTHORIZED);
	}
}
