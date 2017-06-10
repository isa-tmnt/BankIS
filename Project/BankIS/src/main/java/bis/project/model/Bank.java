package bis.project.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bis.project.security.User;

@Entity
public class Bank {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(nullable=false, length=3)
	private int bankCode;
	
	@Column(nullable=false, length=50)
	private String name;
	
	@Column(nullable=false, length=8)
	private String swiftCode;
	
	@Column(nullable=false, length=18)
	private String billingAccount;
	
	@OneToMany(mappedBy="bank")
	@JsonIgnore
	private Set<BankAccount> accounts;
	
	@OneToMany(mappedBy="senderBank")
	@JsonIgnore
	private Set<InterbankTransfer> sentTransfers;
	
	@OneToMany(mappedBy="recipientBank")
	@JsonIgnore
	private Set<InterbankTransfer> receivedTransfers;
	
	@OneToMany(mappedBy="bank")
	@JsonIgnore
	private Set<User> users;
	
	public Bank() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getBankCode() {
		return bankCode;
	}

	public void setBankCode(int bankCode) {
		this.bankCode = bankCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getBillingAccount() {
		return billingAccount;
	}

	public void setBillingAccount(String billingAccount) {
		this.billingAccount = billingAccount;
	}

	public Set<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<BankAccount> accounts) {
		this.accounts = accounts;
	}

	public Set<InterbankTransfer> getSentTransfers() {
		return sentTransfers;
	}

	public void setSentTransfers(Set<InterbankTransfer> sentTransfers) {
		this.sentTransfers = sentTransfers;
	}

	public Set<InterbankTransfer> getReceivedTransfers() {
		return receivedTransfers;
	}

	public void setReceivedTransfers(Set<InterbankTransfer> receivedTransfers) {
		this.receivedTransfers = receivedTransfers;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
