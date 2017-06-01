package bis.project.services;

import java.util.Set;

import bis.project.security.Role;

public interface RoleServices {
	
	public Set<Role> getAllRoles();
	
	public Role getRole(Integer id);
	
	public Role addRole(Role role);
	
	public Role updateRole(Role role);
	
	public void deleteRole(Integer id);
}
