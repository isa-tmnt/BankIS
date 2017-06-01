package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.repositories.RoleRepository;
import bis.project.security.Role;

@Service
public class RoleServicesImpl implements RoleServices {

	@Autowired
	private RoleRepository repository;
	
	@Override
	public Set<Role> getAllRoles() {
		Set<Role> roles = new HashSet<Role>();
		for(Role role : repository.findAll()) {
			roles.add(role);
		}
		
		return roles;
	}

	@Override
	public Role getRole(Integer id) {
		Role role = repository.findOne(id);
		
		if(role != null) {
			return role;
		}
		
		return null;
	}

	@Override
	public Role addRole(Role role) {
		return repository.save(role);
	}

	@Override
	public Role updateRole(Role role) {
		return repository.save(role);
	}

	@Override
	public void deleteRole(Integer id) {
		Role role = repository.findOne(id);
		
		if(role != null) {
			repository.delete(id);
		}
	}
}
