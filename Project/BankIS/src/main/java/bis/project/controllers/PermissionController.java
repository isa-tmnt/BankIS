package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.security.Permission;
import bis.project.services.PermissionServices;

@RestController
public class PermissionController {
	
	@Autowired
	private PermissionServices services;
	
	@RequestMapping(value = "/api/permissions", 
					method = RequestMethod.GET)
	public Set<Permission> getAllPermissions() {
		Set<Permission> permissions = services.getAllPermissions();
		return permissions;
	}
	
	@RequestMapping(value = "/api/permissions/{id}", 
					method = RequestMethod.GET)
	public Permission getPermission(@PathVariable("id") Integer id) {
		return services.getPermission(id);
	}
	
	@RequestMapping(value = "/api/permissions", 
					method = RequestMethod.POST)
	public Permission addPermission(@RequestBody Permission permission) {
		return services.addPermission(permission);
	}
	
	@RequestMapping(value = "/api/permissions/{id}", 
					method = RequestMethod.PUT)
	public Permission updatePermission(@PathVariable("id") Integer id, @RequestBody Permission permission) {
		permission.setId(id);
		return services.updatePermission(permission);
	}
	
	@RequestMapping(value = "/api/permissions/{id}", 
					method = RequestMethod.DELETE)
	public void deletePermission(@PathVariable("id") Integer id) {
		services.deletePermission(id);
	}
}
