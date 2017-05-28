package bis.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TransferItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(optional=false)
	private InterbankTransfer transfer;
	
	@ManyToOne(optional=false)
	private BankOrder bankOrder;
	
	public TransferItem() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InterbankTransfer getTransfer() {
		return transfer;
	}

	public void setTransfer(InterbankTransfer transfer) {
		this.transfer = transfer;
	}

	public BankOrder getBankOrder() {
		return bankOrder;
	}

	public void setBankOrder(BankOrder bankOrder) {
		this.bankOrder = bankOrder;
	}
}
