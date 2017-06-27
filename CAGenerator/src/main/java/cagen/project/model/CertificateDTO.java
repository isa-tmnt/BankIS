package cagen.project.model;

public class CertificateDTO {
	
	private String alias;
	private String keyAlias;
	private String commonName;			//CN
	private String surname;
	private String givenName;
	private String organizationUnit;	//OU
	private String organizationName;	//O
	private String country;				//C
	private String email;				//E
	private String issuer;
	
	private boolean ca;
	private boolean selfSigned;
	
	private KeyStoreDTO issuerKeyStore;
	private KeyStoreDTO keyStore;
	
	public CertificateDTO() {}

	public CertificateDTO(String alias, String keyAlias, String commonName, String surname, String givenName,
			String organizationUnit, String organizationName, String country, String email, String issuer,
			boolean ca, boolean selfSigned, KeyStoreDTO issuerKeyStore,
			KeyStoreDTO keyStore) {
		super();
		this.alias = alias;
		this.keyAlias = keyAlias;
		this.commonName = commonName;
		this.surname = surname;
		this.givenName = givenName;
		this.organizationUnit = organizationUnit;
		this.organizationName = organizationName;
		this.country = country;
		this.email = email;
		this.issuer = issuer;
		this.ca = ca;
		this.selfSigned = selfSigned;
		this.issuerKeyStore = issuerKeyStore;
		this.keyStore = keyStore;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getKeyAlias() {
		return keyAlias;
	}

	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
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

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public boolean isCa() {
		return ca;
	}

	public void setCa(boolean ca) {
		this.ca = ca;
	}

	public boolean isSelfSigned() {
		return selfSigned;
	}

	public void setSelfSigned(boolean selfSigned) {
		this.selfSigned = selfSigned;
	}

	public KeyStoreDTO getIssuerKeyStore() {
		return issuerKeyStore;
	}

	public void setIssuerKeyStore(KeyStoreDTO issuerKeyStore) {
		this.issuerKeyStore = issuerKeyStore;
	}

	public KeyStoreDTO getKeyStore() {
		return keyStore;
	}

	public void setKeyStore(KeyStoreDTO keyStore) {
		this.keyStore = keyStore;
	}
}
