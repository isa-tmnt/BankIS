package bis.project.security;

import java.util.HashSet;
import java.util.Set;

import bis.project.model.Bank;

public class UserResponse {
	
	private String firstName;
	private String lastName;
    private String email;
	private Bank bank;
	private Set<Role> roles = new HashSet<Role>();
	
	public UserResponse() {}

	public UserResponse(String firstName, String lastName, String email, Bank bank) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.bank = bank;
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

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
