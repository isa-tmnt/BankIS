package bis.project.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="CLIENT_TYPE",
		discriminatorType=DiscriminatorType.STRING
)
public class ClientDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
	
	@Column(nullable=false, unique=true, length=13)
	protected String jmbg;
	
	@Column(nullable=false, length=50)
	protected String firstName;
	
	@Column(nullable=false, length=50)
	protected String lastName;
	
	@Column(length=80)
	protected String address;
	
	@Column(length=80)
	protected String email;
	
	@Column(length=30)
	protected String phoneNumber;
	
	@OneToMany(mappedBy="client")
	@JsonIgnore
	protected Set<BankAccount> accounts;
	
	public ClientDetails() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<BankAccount> accounts) {
		this.accounts = accounts;
	}
}
