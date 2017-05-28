package bis.project.model;

import java.math.BigDecimal;
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
public class InterbankTransfer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date transferDate;
	
	@Column(nullable=false, scale=2)
	private BigDecimal amount;
	
	@ManyToOne(optional=false)
	private BankMessages bankMessage;
	
	@ManyToOne(optional=false)
	private Bank senderBank;
	
	@ManyToOne(optional=false)
	private Bank recipientBank;
	
	public InterbankTransfer() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BankMessages getBankMessage() {
		return bankMessage;
	}

	public void setBankMessage(BankMessages bankMessage) {
		this.bankMessage = bankMessage;
	}

	public Bank getSenderBank() {
		return senderBank;
	}

	public void setSenderBank(Bank senderBank) {
		this.senderBank = senderBank;
	}

	public Bank getRecipientBank() {
		return recipientBank;
	}

	public void setRecipientBank(Bank recipientBank) {
		this.recipientBank = recipientBank;
	}
}
