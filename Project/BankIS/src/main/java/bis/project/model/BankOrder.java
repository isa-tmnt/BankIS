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

@Entity
public class BankOrder {	//Analitika izvoda
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date bankOrderDate;		//datum analitike
	
	@Column(nullable=false, length=1)
	private String direction;

	@Column(length=255)
	private String debtor;	
	
	@Column(length=255)
	private String purposeOfPayment;
	
	@Column(length=255)
	private String recipient;
	
	@Temporal(TemporalType.DATE)
	private Date orderDate;			//datum naloga
	
	@Temporal(TemporalType.DATE)
	private Date currencyDate;
	
	@Column(length=18)
	private String firstAccount;
	
	@Column(length=2)
	private String firstModel;
	
	@Column(length=22)
	private String firstNumber;		//poziv na broj
	
	@Column(length=18)
	private String secondAccount;
	
	@Column(length=2)
	private String secondModel;
	
	@Column(length=22)
	private String secondNumber;	//poziv na broj
	
	@Column(nullable=false, precision=15, scale=2)
	private BigDecimal amount;
	
	private boolean urgently;
	
	@ManyToOne(optional=false)
	private DailyAccountBalance dailyAccountBalance;
	
	@OneToMany(mappedBy="bankOrder")
	private Set<ClosingAccount> closingAccounts;
	
	@OneToMany(mappedBy="bankOrder")
	private Set<TransferItem> transferItems;
	
	public BankOrder() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBankOrderDate() {
		return bankOrderDate;
	}

	public void setBankOrderDate(Date bankOrderDate) {
		this.bankOrderDate = bankOrderDate;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDebtor() {
		return debtor;
	}

	public void setDebtor(String debtor) {
		this.debtor = debtor;
	}

	public String getPurposeOfPayment() {
		return purposeOfPayment;
	}

	public void setPurposeOfPayment(String purposeOfPayment) {
		this.purposeOfPayment = purposeOfPayment;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getCurrencyDate() {
		return currencyDate;
	}

	public void setCurrencyDate(Date currencyDate) {
		this.currencyDate = currencyDate;
	}

	public String getFirstAccount() {
		return firstAccount;
	}

	public void setFirstAccount(String firstAccount) {
		this.firstAccount = firstAccount;
	}

	public String getFirstModel() {
		return firstModel;
	}

	public void setFirstModel(String firstModel) {
		this.firstModel = firstModel;
	}

	public String getFirstNumber() {
		return firstNumber;
	}

	public void setFirstNumber(String firstNumber) {
		this.firstNumber = firstNumber;
	}

	public String getSecondAccount() {
		return secondAccount;
	}

	public void setSecondAccount(String secondAccount) {
		this.secondAccount = secondAccount;
	}

	public String getSecondModel() {
		return secondModel;
	}

	public void setSecondModel(String secondModel) {
		this.secondModel = secondModel;
	}

	public String getSecondNumber() {
		return secondNumber;
	}

	public void setSecondNumber(String secondNumber) {
		this.secondNumber = secondNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isUrgently() {
		return urgently;
	}

	public void setUrgently(boolean urgently) {
		this.urgently = urgently;
	}

	public DailyAccountBalance getDailyAccountBalance() {
		return dailyAccountBalance;
	}

	public void setDailyAccountBalance(DailyAccountBalance dailyAccountBalance) {
		this.dailyAccountBalance = dailyAccountBalance;
	}

	public Set<ClosingAccount> getClosingAccounts() {
		return closingAccounts;
	}

	public void setClosingAccounts(Set<ClosingAccount> closingAccounts) {
		this.closingAccounts = closingAccounts;
	}

	public Set<TransferItem> getTransferItems() {
		return transferItems;
	}

	public void setTransferItems(Set<TransferItem> transferItems) {
		this.transferItems = transferItems;
	}
}
