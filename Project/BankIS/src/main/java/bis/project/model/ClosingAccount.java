package bis.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ClosingAccount {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=18)
	private String switchToAnAccount;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@ManyToOne(optional=false)
	private BankAccount account;
	
	@ManyToOne
	private BankOrder bankOrder;
	
	public ClosingAccount() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSwitchToAnAccount() {
		return switchToAnAccount;
	}

	public void setSwitchToAnAccount(String switchToAnAccount) {
		this.switchToAnAccount = switchToAnAccount;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

	public BankOrder getBankOrder() {
		return bankOrder;
	}

	public void setBankOrder(BankOrder bankOrder) {
		this.bankOrder = bankOrder;
	}
}
