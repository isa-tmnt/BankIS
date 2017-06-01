package bis.project.repositories;

import org.springframework.data.repository.CrudRepository;

import bis.project.security.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Integer> {

}
