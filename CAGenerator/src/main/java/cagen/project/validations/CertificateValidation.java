package cagen.project.validations;

import cagen.project.model.CertificateDTO;

public class CertificateValidation {
	
	public static boolean validate(CertificateDTO dto) {
		
		if(dto.getAlias() == null || dto.getAlias() == "") {
			return false;
		} else if(dto.getCommonName() == null || dto.getCommonName() == "") {
			return false;
		} else if(dto.getCountry() == null || dto.getCountry() == "") {
			return false;
		} else if(dto.getEmail() == null || dto.getEmail() == "") {
			return false;
		} else if(dto.getGivenName() == null || dto.getGivenName() == "") {
			return false;
		} else if(dto.getKeyAlias() == null || dto.getKeyAlias() == "") {
			return false;
		} else if(dto.getOrganizationName() == null || dto.getOrganizationName() == "") {
			return false;
		} else if(dto.getOrganizationUnit() == null || dto.getOrganizationUnit() == "") {
			return false;
		} else if(dto.getSurname() == null || dto.getSurname() == "") {
			return false;
		} else if(dto.getKeyStore().getAlias() == null || dto.getKeyStore().getAlias() == "") {
			return false;
		} else if(dto.getKeyStore().getPassword() == null || dto.getKeyStore().getPassword() == "") {
			return false;
		} else if(!dto.isSelfSigned() && (dto.getIssuer() == null || dto.getIssuer() == "")) {
			return false;
		} else if(!dto.isSelfSigned() && (dto.getIssuerKeyStore().getAlias() == null || dto.getIssuerKeyStore().getAlias() == "")) {
			return false;
		} else if(!dto.isSelfSigned() && (dto.getIssuerKeyStore().getPassword() == null || dto.getIssuerKeyStore().getPassword() == "")) {
			return false;
		}
		
		return true;
	}
}
