package cagen.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cagen.project.model.CertificateDTO;
import cagen.project.model.CertificateResponse;
import cagen.project.model.KeyStoreDTO;
import cagen.project.services.CertificateServices;

@RestController
public class CertificateController {
	
	@Autowired
	private CertificateServices services;
	
	@RequestMapping(value = "/api/certificates", 
					method = RequestMethod.POST)
	public ResponseEntity<CertificateDTO> genCertificate(@RequestBody CertificateDTO dto) {
		CertificateDTO response = services.genCertificate(dto);
		
		if(response != null) {
			return new ResponseEntity<CertificateDTO>(response, HttpStatus.OK);
		}
		
		return new ResponseEntity<CertificateDTO>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/api/certificates/{alias}", 
					method = RequestMethod.POST)
	public ResponseEntity<CertificateResponse> getCertificate(@PathVariable("alias") String alias, @RequestBody KeyStoreDTO keyStore) {
		CertificateResponse response = services.getCertificate(alias, keyStore);
		
		if(response != null) {
			return new ResponseEntity<CertificateResponse>(response, HttpStatus.OK);
		}
		
		return new ResponseEntity<CertificateResponse>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/api/certificates/export/{alias}", 
					method = RequestMethod.POST)
	public ResponseEntity<CertificateResponse> exportCertificate(@PathVariable("alias") String alias, @RequestBody KeyStoreDTO keyStore) {
		CertificateResponse response = services.exportCertificate(alias, keyStore);
		
		if(response != null) {
			return new ResponseEntity<CertificateResponse>(response, HttpStatus.OK);
		}
		
		return new ResponseEntity<CertificateResponse>(HttpStatus.NOT_FOUND);
	}
}
