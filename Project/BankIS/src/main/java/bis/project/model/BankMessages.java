package bis.project.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BankMessages {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, unique=true, length=20)
	private String messageCode;
	
	@Column(length=255)
	private String description;
	
	@OneToMany(mappedBy="bankMessage")
	@JsonIgnore
	private Set<InterbankTransfer> transfers;
	
	public BankMessages() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<InterbankTransfer> getTransfers() {
		return transfers;
	}

	public void setTransfers(Set<InterbankTransfer> transfers) {
		this.transfers = transfers;
	}
}
