package cagen.project.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;

import org.springframework.stereotype.Service;

import cagen.project.model.KeyStoreDTO;

@Service
public class KeyStoreServicesImpl implements KeyStoreServices {
	
	private KeyStore keyStore;
	
	public KeyStoreServicesImpl() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	@Override
	public KeyStoreDTO genKeyStore(KeyStoreDTO dto) {
		
		if(dto.getAlias() == null || dto.getAlias() == "") {
			return null;
		}
		
		if(dto.getPassword() == null || dto.getPassword() == "") {
			return null;
		}
		
		try {
			if(keyStore == null) {
				keyStore = KeyStore.getInstance("BKS", "BC");
			}
			
			keyStore.load(null, dto.getPassword().toCharArray());
			keyStore.store(new FileOutputStream("./files/" + dto.getAlias() +".jks"), dto.getPassword().toCharArray());
			
			return dto;
		} catch (KeyStoreException e) {
			e.getMessage();
		} catch (NoSuchProviderException e) {
			e.getMessage();
		} catch (NoSuchAlgorithmException e) {
			e.getMessage();
		} catch (CertificateException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		
		return null;
	}
}
