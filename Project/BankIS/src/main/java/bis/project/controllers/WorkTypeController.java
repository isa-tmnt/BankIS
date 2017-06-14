package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.M103Client;
import bis.project.IAmWsClientConfig;
import bis.project.model.WorkType;
import bis.project.services.CredentialsServices;
import bis.project.services.WorkTypeServices;
import bis.project.validators.ValidationException;
import bis.project.validators.WorkTypeValidator;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeRequest;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeResponse;

@RestController
public class WorkTypeController {
	
	@Autowired
	private WorkTypeServices services;
	
	@Autowired
	private CredentialsServices cServices;
	
	@RequestMapping(value = "/api/worktypes", 
					method = RequestMethod.GET)
	public ResponseEntity<Set<WorkType>> getAllWorkTypes(@RequestHeader(value="Authorization") String basicAuth) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IAmWsClientConfig.class);
	     M103Client wsclient = /*new BankOrderClient();*/ context.getBean(M103Client.class);
	    wsclient.setDefaultUri("http://localhost:7779/ws");
	    GetMOneZeroThreeRequest request = new GetMOneZeroThreeRequest();
	    request.setId(123);
	    request.setSifraValute("asdgasd");
		GetMOneZeroThreeResponse response = wsclient.getBeer(request);
		
		
		
		
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "getAllWorkTypes");
		
		if(isAuthorized) {
			Set<WorkType> workTypes = services.getAllWorkTypes();
			return new ResponseEntity<Set<WorkType>>(workTypes, HttpStatus.OK);
		}
		
		return new ResponseEntity<Set<WorkType>>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/worktypes/{id}", 
					method = RequestMethod.GET)
	public ResponseEntity<WorkType> getWorkType(@PathVariable("id") Integer id, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "getWorkType");
		
		if(isAuthorized) {
			WorkType workType = services.getWorkType(id);
			
			if(workType != null) {
				return new ResponseEntity<WorkType>(workType, HttpStatus.OK);
			}
			
			return new ResponseEntity<WorkType>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<WorkType>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/worktypes", 
					method = RequestMethod.POST)
	public ResponseEntity<WorkType> addWorkType(@RequestBody WorkType workType, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "addWorkType");
		
		if(isAuthorized) {
			WorkTypeValidator.Validate(workType);
			WorkType wt = services.addWorkType(workType);
			return new ResponseEntity<WorkType>(wt, HttpStatus.OK);
		}
		
		return new ResponseEntity<WorkType>(HttpStatus.UNAUTHORIZED); 
	}
	
	@RequestMapping(value = "/api/worktypes/{id}", 
					method = RequestMethod.PUT)
	public ResponseEntity<WorkType> updateWorkType(@PathVariable("id") Integer id, @RequestBody WorkType workType, @RequestHeader(value="Authorization") String basicAuth) throws ValidationException {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "updateWorkType");
		
		if(isAuthorized) {
			WorkTypeValidator.Validate(workType);
			workType.setId(id);
			WorkType wt = services.updateWorkType(workType);
			return new ResponseEntity<WorkType>(wt, HttpStatus.OK);
		}
		
		return new ResponseEntity<WorkType>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/api/worktypes/{id}", 
					method = RequestMethod.DELETE)
	public ResponseEntity<WorkType> deleteWorkType(@PathVariable("id") Integer id, @RequestHeader(value="Authorization") String basicAuth) {
		boolean isAuthorized = cServices.isAuthorized(basicAuth, "deleteWorkType");
		
		if(isAuthorized) {
			services.deleteWorkType(id);
			return new ResponseEntity<WorkType>(HttpStatus.OK);
		}
		
		return new ResponseEntity<WorkType>(HttpStatus.UNAUTHORIZED);
	}
}
