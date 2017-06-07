package bis.project.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class M103 {
	
	private Integer id;
	
	private String debtorBankSwift;

	
	public M103(){}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDebtorBankSwift() {
		return debtorBankSwift;
	}

	public void setDebtorBankSwift(String debtorBankSwift) {
		this.debtorBankSwift = debtorBankSwift;
	}
	
	
}
