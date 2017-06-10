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
import javax.persistence.ManyToOne;

import bis.project.model.Bank;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=80)
	private String firstName;
    
	@Column(nullable=false, length=80)
	private String lastName;
	
	@Column(nullable=false, unique=true, length=120)
    private String email;
	
	@Column(nullable=false, length=255)
    private String password;
	
	@Column(nullable=false, length=255)
	private String salt; 
	
	@ManyToOne(optional=false)
	private Bank bank;
	
    //private boolean enabled;
    
    //private boolean tokenExpired;
    
    @ManyToMany
    @JoinTable( 
    	name = "users_roles", 
        joinColumns = @JoinColumn(
        	name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
        	name = "role_id", referencedColumnName = "id")) 
    private Set<Role> roles;
	
	public User() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
}
