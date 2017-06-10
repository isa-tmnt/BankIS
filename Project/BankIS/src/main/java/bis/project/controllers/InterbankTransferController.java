package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.InterbankTransfer;
import bis.project.services.CredentialsServices;
import bis.project.services.InterbankTransferServices;
import bis.project.validators.ValidationException;

@RestController
public class InterbankTransferController {
	
	@Autowired
	private InterbankTransferServices interbankServices;
	
	@Autowired
	private CredentialsServices services;
	
	@RequestMapping(value = "/api/interbankTransfers", 
					method = RequestMethod.GET)
	public Set<InterbankTransfer> getAllCurrencies() {
		Set<InterbankTransfer> currencies = interbankServices.getAllInterbankTrasfers();
		return currencies;
	}
	
	@RequestMapping(value = "/api/interbankTransfers/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<InterbankTransfer> getinterbankTransfer(@PathVariable("id") Integer id) {
		InterbankTransfer interbankTransfer = interbankServices.getInterbankTransfer(id);
		
		if(interbankTransfer != null) {
			return new ResponseEntity<InterbankTransfer>(interbankTransfer, HttpStatus.OK);
		}
		
		return new ResponseEntity<InterbankTransfer>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/api/interbankTransfers", 
					method = RequestMethod.POST)
	public ResponseEntity<InterbankTransfer> addinterbankTransfer(@RequestBody InterbankTransfer interbankTransfer, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException {
	//	boolean isAuthorized = services.isAuthorized(basicAuth, "AddInterbankTransfer");
		
	//	if(isAuthorized) {
			InterbankTransfer c = interbankServices.addInterbankTransfer(interbankTransfer);
			return new ResponseEntity<InterbankTransfer>(c, HttpStatus.OK);
//		}
		
	//	return new ResponseEntity<InterbankTransfer>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/interbankTransfers/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<InterbankTransfer> updateinterbankTransfer(@PathVariable("id") Integer id, @RequestBody InterbankTransfer interbankTransfer, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException{
	//	boolean isAuthorized = services.isAuthorized(basicAuth, "UpdateinterbankTransfer");
		
	//	if(isAuthorized) {
			interbankTransfer.setId(id);
			InterbankTransfer c = interbankServices.updateInterbankTransfer(interbankTransfer);
			return new ResponseEntity<InterbankTransfer>(c, HttpStatus.OK);
	//	}
		
	//	return new ResponseEntity<InterbankTransfer>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/interbankTransfers/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<InterbankTransfer> deleteinterbankTransfer(@PathVariable("id") Integer id, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = services.isAuthorized(basicAuth, "DeleteinterbankTransfer");
		
		if(isAuthorized) {
			interbankServices.deleteInterbankTrasfer(id);
			return new ResponseEntity<InterbankTransfer>(HttpStatus.OK);
		}
		
		return new ResponseEntity<InterbankTransfer>(HttpStatus.UNAUTHORIZED);
	}
}
