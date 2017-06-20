package cagen.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cagen.project.model.CAData;
import cagen.project.model.CertificateDTO;
import cagen.project.model.CertificateResponse;
import cagen.project.model.KeyStoreDTO;
import cagen.project.services.CertificateServices;
import cagen.project.services.CredentialsServices;

@RestController
public class CertificateController {
	
	@Autowired
	private CertificateServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/certificates", 
					method = RequestMethod.POST)
	public ResponseEntity<CertificateDTO> genCertificate(@RequestBody CertificateDTO dto, 
														 @RequestHeader(value="CsrfToken") String csrfToken, 
														 @RequestHeader(value="AuthEmail") String authEmail, 
														 @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "genCertificate");
		
		if(isAuthorized) {
			CertificateDTO response = services.genCertificate(dto);
			
			if(response != null) {
				return new ResponseEntity<CertificateDTO>(response, HttpStatus.OK);
			}
			
			return new ResponseEntity<CertificateDTO>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<CertificateDTO>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/certificates/{alias}", 
					method = RequestMethod.POST)
	public ResponseEntity<CertificateResponse> getCertificate(@PathVariable("alias") String alias, @RequestBody KeyStoreDTO keyStore, 
															  @RequestHeader(value="CsrfToken") String csrfToken, 
															  @RequestHeader(value="AuthEmail") String authEmail, 
															  @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "getCertificate");
		
		if(isAuthorized) {
			CertificateResponse response = services.getCertificate(alias, keyStore);
			
			if(response != null) {
				return new ResponseEntity<CertificateResponse>(response, HttpStatus.OK);
			}
			
			return new ResponseEntity<CertificateResponse>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<CertificateResponse>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/certificates/export/{alias}", 
					method = RequestMethod.POST)
	public ResponseEntity<CertificateResponse> exportCertificate(@PathVariable("alias") String alias, @RequestBody KeyStoreDTO keyStore, 
																 @RequestHeader(value="CsrfToken") String csrfToken, 
																 @RequestHeader(value="AuthEmail") String authEmail, 
																 @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "exportCertificate");
		
		if(isAuthorized) {
			CertificateResponse response = services.exportCertificate(alias, keyStore);
			
			if(response != null) {
				return new ResponseEntity<CertificateResponse>(response, HttpStatus.OK);
			}
			
			return new ResponseEntity<CertificateResponse>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<CertificateResponse>(HttpStatus.UNAUTHORIZED);
	}
	
	public ResponseEntity<CertificateResponse> genCertificateRequest(@RequestBody CAData caData, 
																 	 @RequestHeader(value="CsrfToken") String csrfToken, 
																 	 @RequestHeader(value="AuthEmail") String authEmail, 
																 	 @CookieValue("jwt") String jwt) {
		
		boolean isAuthorized = false;
		isAuthorized = cServices.isJWTAuthorized(jwt, csrfToken, authEmail, "genCertificateRequest");
		
		if(isAuthorized) {
			
		}
		
		return new ResponseEntity<CertificateResponse>(HttpStatus.UNAUTHORIZED);
	}
}
