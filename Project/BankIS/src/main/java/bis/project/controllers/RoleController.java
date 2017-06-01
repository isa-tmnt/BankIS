package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.security.Role;
import bis.project.services.RoleServices;

@RestController
public class RoleController {
	
	@Autowired
	private RoleServices services;
	
	@RequestMapping(value = "/api/roles", 
					method = RequestMethod.GET)
	public Set<Role> getAllRoles() {
		Set<Role> roles = services.getAllRoles();
		
		return roles;
	}
	
	@RequestMapping(value = "/api/roles/{id}", 
					method = RequestMethod.GET)
	public Role getRole(@PathVariable("id") Integer id) {
		return services.getRole(id);
	}
	
	@RequestMapping(value = "/api/roles", 
					method = RequestMethod.POST)
	public Role addRole(@RequestBody Role role) {
		return services.addRole(role);
	}
	
	@RequestMapping(value = "/api/roles/{id}", 
					method = RequestMethod.PUT)
	public Role updateRole(@PathVariable("id") Integer id, @RequestBody Role role) {
		role.setId(id);
		return services.updateRole(role);
	}
	
	@RequestMapping(value = "/api/roles/{id}", 
					method = RequestMethod.DELETE)
	public void deleteRole(@PathVariable("id") Integer id) {
		services.deleteRole(id);
	}
}
