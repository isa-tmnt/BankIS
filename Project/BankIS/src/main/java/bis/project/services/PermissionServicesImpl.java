package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.repositories.PermissionRepository;
import bis.project.security.Permission;

@Service
public class PermissionServicesImpl implements PermissionServices {

	@Autowired
	private PermissionRepository repository;
	
	@Override
	public Set<Permission> getAllPermissions() {
		Set<Permission> permissions = new HashSet<Permission>();
		for(Permission permission : repository.findAll()) {
			permissions.add(permission);
		}
		
		return permissions;
	}

	@Override
	public Permission getPermission(Integer id) {
		Permission permission = repository.findOne(id);
		
		if(permission != null) {
			return permission;
		}
		
		return null;
	}

	@Override
	public Permission addPermission(Permission permission) {
		return repository.save(permission);
	}

	@Override
	public Permission updatePermission(Permission permission) {
		return repository.save(permission);
	}

	@Override
	public void deletePermission(Integer id) {
		Permission permission = repository.findOne(id);
		
		if(permission != null) {
			repository.delete(id);
		}
	}
}
