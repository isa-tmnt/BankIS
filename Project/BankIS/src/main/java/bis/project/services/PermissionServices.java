package bis.project.services;

import java.util.Set;

import bis.project.security.Permission;

public interface PermissionServices {
	
	public Set<Permission> getAllPermissions();
	
	public Permission getPermission(Integer id);
	
	public Permission addPermission(Permission permission);
	
	public Permission updatePermission(Permission permission);
	
	public void deletePermission(Integer id);
}
