package cagen.project.model;

public class CAData {
	
	private String commonName;			//CN
	private String surname;
	private String givenName;
	private String organizationUnit;	//OU
	private String organizationName;	//O
	private String country;				//C
	private String email;				//E
	
	public CAData() {}

	public CAData(String commonName, String surname, String givenName, String organizationUnit,
			String organizationName, String country, String email) {
		super();
		this.commonName = commonName;
		this.surname = surname;
		this.givenName = givenName;
		this.organizationUnit = organizationUnit;
		this.organizationName = organizationName;
		this.country = country;
		this.email = email;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
