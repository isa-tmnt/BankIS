package bis.project.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=18)
	private String accountNumber;
	
	@Column(nullable=false, length=1)
	private String status;	//A - active, D - deactivated
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@ManyToOne(optional=false)
	private Bank bank;
	
	@ManyToOne(optional=false)
	private Currency currency;
	
	@ManyToOne(optional=false)
	private ClientDetails client;
	
	@OneToMany(mappedBy="account")
	@JsonIgnore
	private Set<ClosingAccount> closingAccounts;
	
	@OneToMany(mappedBy="account")
	@JsonIgnore
	private Set<DailyAccountBalance> dailyBalances;
	
	public BankAccount() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public ClientDetails getClient() {
		return client;
	}

	public void setClient(ClientDetails client) {
		this.client = client;
	}

	public Set<ClosingAccount> getClosingAccounts() {
		return closingAccounts;
	}

	public void setClosingAccounts(Set<ClosingAccount> closingAccounts) {
		this.closingAccounts = closingAccounts;
	}

	public Set<DailyAccountBalance> getDailyBalances() {
		return dailyBalances;
	}

	public void setDailyBalances(Set<DailyAccountBalance> dailyBalances) {
		this.dailyBalances = dailyBalances;
	}
}
