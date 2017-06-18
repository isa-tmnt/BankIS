package cagen.project.services;

import java.security.cert.Certificate;

import cagen.project.model.CertificateDTO;
import cagen.project.model.CertificateResponse;
import cagen.project.model.KeyStoreDTO;

public interface CertificateServices {
	
	public CertificateDTO genCertificate(CertificateDTO dto);
	
	public CertificateResponse getCertificate(String alias, KeyStoreDTO dto);
	
	public CertificateResponse exportCertificate(String alias, KeyStoreDTO dto);
	
	public CertificateResponse parseCertificate(Certificate certificate);
}
