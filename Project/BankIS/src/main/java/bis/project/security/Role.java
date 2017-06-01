package bis.project.security;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=255)
	private String name;
	
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;
    
    @ManyToMany
    @JoinTable(
    	name = "roles_permissions", 
    	joinColumns = @JoinColumn(
    		name = "role_id", referencedColumnName = "id"), 
    	inverseJoinColumns = @JoinColumn(
            name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions;
	
	public Role() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
}
