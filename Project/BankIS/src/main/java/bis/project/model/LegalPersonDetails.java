package bis.project.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("LEGAL_PERSON")
public class LegalPersonDetails extends ClientDetails {
	
	@Column(unique=true, length=9)
	private String pib;
	
	@Column(length=30)
	private String fax;
	
	@Column(length=255)
	private String webPage;
	
	@ManyToOne
	private WorkType workType;
	
	public LegalPersonDetails() {}

	public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}
}
