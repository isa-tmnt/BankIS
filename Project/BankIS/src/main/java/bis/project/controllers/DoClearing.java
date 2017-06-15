package bis.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.services.ClearingAndSService;


@RestController
public class DoClearing {
	
	@Autowired
	private ClearingAndSService clearingService;
	
	
	@RequestMapping(value = "/doClearing", 
			method = RequestMethod.GET)
	public void doIt() {
		
		clearingService.DoIt();
		
		
	}
}
