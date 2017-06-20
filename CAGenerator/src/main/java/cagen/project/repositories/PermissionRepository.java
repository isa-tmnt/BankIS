package cagen.project.repositories;

import org.springframework.data.repository.CrudRepository;

import cagen.project.security.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Integer> {

}
