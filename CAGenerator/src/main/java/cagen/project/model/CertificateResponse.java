package cagen.project.model;

import java.util.Date;

public class CertificateResponse {
	
	private CAData subject;
	private CAData issuer;
	private int version;
	
	private Date startDate;
	private Date endDate;
	
	//private PublicKey publicKey;
	
	public CertificateResponse() {}

	public CertificateResponse(CAData subject, CAData issuer, int version, Date startDate, Date endDate) {
		super();
		this.subject = subject;
		this.issuer = issuer;
		this.version = version;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public CAData getSubject() {
		return subject;
	}

	public void setSubject(CAData subject) {
		this.subject = subject;
	}

	public CAData getIssuer() {
		return issuer;
	}

	public void setIssuer(CAData issuer) {
		this.issuer = issuer;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
}
