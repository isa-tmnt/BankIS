package cagen.project.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;

import cagen.project.model.CertificateDTO;
import cagen.project.model.CertificateResponse;
import cagen.project.model.CAData;
import cagen.project.model.KeyStoreDTO;
import cagen.project.validations.CertificateValidation;

@Service
public class CertificateServicesImpl implements CertificateServices {
	
	private KeyStore keyStore;
	private KeyStore issuerKeyStore;
	
	public CertificateServicesImpl() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	@Override
	public CertificateDTO genCertificate(CertificateDTO dto) {
		boolean isValid = CertificateValidation.validate(dto);
		
		if(!isValid) return null;
		
		//"./files/" + dto.getKeyStore().getAlias() + ".jks"
		String keyStorePath = "./files/" + dto.getKeyStore().getAlias() + ".jks";
		char[] keyStorePassword = dto.getKeyStore().getPassword().toCharArray();
		
		String issuerKeyStorePath = "./files/" + dto.getIssuerKeyStore().getAlias() + ".jks";
		char[] issuerKeyStorePassword = dto.getIssuerKeyStore().getPassword().toCharArray();
		
		try {
			if(keyStore == null) {
				keyStore = KeyStore.getInstance("BKS", "BC");
			}

			keyStore.load(new FileInputStream(keyStorePath), keyStorePassword);
			
			if(issuerKeyStore == null) {
				issuerKeyStore = KeyStore.getInstance("BKS", "BC");
			}
			
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			builder.addRDN(BCStyle.CN, dto.getCommonName());
			builder.addRDN(BCStyle.SURNAME, dto.getSurname());
			builder.addRDN(BCStyle.GIVENNAME, dto.getGivenName());
			builder.addRDN(BCStyle.O, dto.getOrganizationName());
			builder.addRDN(BCStyle.OU, dto.getOrganizationUnit());
			builder.addRDN(BCStyle.C, dto.getCountry());
			builder.addRDN(BCStyle.E, dto.getEmail());
			builder.addRDN(BCStyle.UID, "");
			
			X500Name name = builder.build();
			BigInteger serial = new BigInteger(20, new SecureRandom());
			
			long nowMillis = System.currentTimeMillis();
		    Date now = new Date(nowMillis);
		    
		    Calendar expDate = Calendar.getInstance();
			expDate.set(Calendar.YEAR, expDate.get(Calendar.YEAR) + 1);
			
			long ttlMillis = expDate.getTimeInMillis();
			Date exp = new Date(ttlMillis);
			
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);

			KeyPair pair = keyGen.generateKeyPair();
			
			JcaContentSignerBuilder sigBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			sigBuilder = sigBuilder.setProvider("BC");
			
			ContentSigner contentSigner = sigBuilder.build(pair.getPrivate());
			
			X509v3CertificateBuilder certGen;
			
			if(dto.isSelfSigned()) {
				certGen = new JcaX509v3CertificateBuilder(name, serial, now, exp, name, pair.getPublic());
			} else {
				issuerKeyStore.load(new FileInputStream(issuerKeyStorePath), issuerKeyStorePassword);
				
				Certificate issuerCertificate = issuerKeyStore.getCertificate(dto.getIssuer());
				
				if (((X509Certificate) issuerCertificate).getNotAfter().before(new Date())) {
					System.out.println("Issuer certificate is no longer valid");
					return null;
				} else if (((X509Certificate) issuerCertificate).getBasicConstraints() == -1) {
					System.out.println("Issuer certificate is not CA!");
					return null;
				}
				
				X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) issuerCertificate).getIssuer();
				
				certGen = new JcaX509v3CertificateBuilder(issuerName, serial, now, exp, name, pair.getPublic());
				certGen.addExtension(Extension.authorityKeyIdentifier, true, new AuthorityKeyIdentifier(issuerCertificate.getEncoded()));
			}
			
			GeneralNames subjectAltName = new GeneralNames(new GeneralName(GeneralName.rfc822Name, "localhost"));
			
			certGen.addExtension(Extension.subjectAlternativeName, true, new DEROctetString(subjectAltName));
			certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(dto.isCa()));
			
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");
			
			keyStore.setCertificateEntry(dto.getAlias(), certConverter.getCertificate(certHolder));
			keyStore.setKeyEntry(dto.getKeyAlias(), pair.getPrivate().getEncoded(),
					new Certificate[] { (Certificate) certConverter.getCertificate(certHolder) });
			
			keyStore.store(new FileOutputStream(keyStorePath), keyStorePassword);
			
			return dto;
		} catch (KeyStoreException e) {
			System.out.println(e.getMessage());
		} catch (NoSuchProviderException e) {
			System.out.println(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		} catch (CertificateException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (OperatorCreationException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	@Override
	public CertificateResponse getCertificate(String alias, KeyStoreDTO dto) {
		
		if(dto.getAlias() == null || dto.getAlias() == "") {
			return null;
		}
		
		if(dto.getPassword() == null || dto.getPassword() == "") {
			return null;
		}
		
		String keyStorePath = "./files/" + dto.getAlias() + ".jks";
		char[] keyStorePassword = dto.getPassword().toCharArray();
		
		try {
			if(keyStore == null) {
				keyStore = KeyStore.getInstance("BKS", "BC");
			}
			
			keyStore.load(new FileInputStream(keyStorePath), keyStorePassword);
			
			Certificate certificate = keyStore.getCertificate(alias);
			
			if(certificate == null) {
				return null;
			}
			
			CertificateResponse response = this.parseCertificate(certificate);
			
			return response;
		} catch (KeyStoreException e) {
			System.out.println(e.getMessage());
		} catch (NoSuchProviderException e) {
			System.out.println(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		} catch (CertificateException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	@Override
	public CertificateResponse exportCertificate(String alias, KeyStoreDTO dto) {
		
		if(dto.getAlias() == null || dto.getAlias() == "") {
			return null;
		}
		
		if(dto.getPassword() == null || dto.getPassword() == "") {
			return null;
		}
		
		String keyStorePath = "./files/" + dto.getAlias() + ".jks";
		char[] keyStorePassword = dto.getPassword().toCharArray();
		
		try {
			if(keyStore == null) {
				keyStore = KeyStore.getInstance("BKS", "BC");
			}
			
			keyStore.load(new FileInputStream(keyStorePath), keyStorePassword);
			
			Certificate certificate = keyStore.getCertificate(alias);
			
			if(certificate == null) {
				return null;
			}
			
			String cerPath = "./files/" + alias + ".cer";
			FileOutputStream os = new FileOutputStream(cerPath);
			os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.write(Base64.encodeBase64(certificate.getEncoded(), true));
			os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.close();
			
			CertificateResponse response = this.parseCertificate(certificate);
			
			return response;
		} catch (KeyStoreException e) {
			System.out.println(e.getMessage());
		} catch (NoSuchProviderException e) {
			System.out.println(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		} catch (CertificateException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	@Override
	public CertificateResponse genCertificateRequest(CAData caData) {
		
		return null;
	}
	
	@Override
	public CertificateResponse parseCertificate(Certificate certificate) {
		X500Name x500name;
		
		try {
			x500name = new JcaX509CertificateHolder((X509Certificate) certificate).getSubject();
			CAData subject = this.createCAData(x500name);
			
			x500name = new JcaX509CertificateHolder((X509Certificate) certificate).getIssuer();
			CAData issuer = this.createCAData(x500name);
			
			int version = ((X509Certificate) certificate).getVersion();
			
			Date startDate = ((X509Certificate) certificate).getNotBefore();
			Date endDate = ((X509Certificate) certificate).getNotAfter();
			
			CertificateResponse response = new CertificateResponse(subject, issuer, version, startDate, endDate);
			
			return response;
		} catch (CertificateEncodingException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	private CAData createCAData(X500Name x500name) {
		RDN commonName = x500name.getRDNs(BCStyle.CN)[0];
		RDN surname = x500name.getRDNs(BCStyle.SURNAME)[0];
		RDN givenName = x500name.getRDNs(BCStyle.GIVENNAME)[0];
		RDN organizationUnit = x500name.getRDNs(BCStyle.OU)[0];
		RDN organizationName = x500name.getRDNs(BCStyle.O)[0];
		RDN country = x500name.getRDNs(BCStyle.C)[0];
		RDN email = x500name.getRDNs(BCStyle.E)[0];
		
		CAData data = new CAData(
				IETFUtils.valueToString(commonName.getFirst().getValue()), 
				IETFUtils.valueToString(surname.getFirst().getValue()), 
				IETFUtils.valueToString(givenName.getFirst().getValue()), 
				IETFUtils.valueToString(organizationUnit.getFirst().getValue()), 
				IETFUtils.valueToString(organizationName.getFirst().getValue()), 
				IETFUtils.valueToString(country.getFirst().getValue()), 
				IETFUtils.valueToString(email.getFirst().getValue()) 
		);
		
		return data;
	}
}
