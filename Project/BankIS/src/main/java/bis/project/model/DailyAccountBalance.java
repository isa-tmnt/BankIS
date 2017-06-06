package bis.project.model;

import java.math.BigDecimal;
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
public class DailyAccountBalance {	//Bank statement
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(precision=14, scale=2)
	private BigDecimal previousState;
	
	@Column(precision=14, scale=2)
	private BigDecimal amountCharged;
	
	@Column(precision=14, scale=2)
	private BigDecimal amountInFavor;
	
	@Column(precision=14, scale=2)
	private BigDecimal newState;
	
	@ManyToOne(optional=false)
	private BankAccount account;
	
	@OneToMany(mappedBy="dailyAccountBalance")
	@JsonIgnore
	private Set<BankOrder> bankOrders;
	
	public DailyAccountBalance() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getPreviousState() {
		return previousState;
	}

	public void setPreviousState(BigDecimal previousState) {
		this.previousState = previousState;
	}

	public BigDecimal getAmountCharged() {
		return amountCharged;
	}

	public void setAmountCharged(BigDecimal amountCharged) {
		this.amountCharged = amountCharged;
	}

	public BigDecimal getAmountInFavor() {
		return amountInFavor;
	}

	public void setAmountInFavor(BigDecimal amountInFavor) {
		this.amountInFavor = amountInFavor;
	}

	public BigDecimal getNewState() {
		return newState;
	}

	public void setNewState(BigDecimal newState) {
		this.newState = newState;
	}

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

	public Set<BankOrder> getBankOrders() {
		return bankOrders;
	}

	public void setBankOrders(Set<BankOrder> bankOrders) {
		this.bankOrders = bankOrders;
	}
}
