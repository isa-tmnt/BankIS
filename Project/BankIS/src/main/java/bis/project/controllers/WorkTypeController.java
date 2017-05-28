package bis.project.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.WorkType;
import bis.project.services.WorkTypeServices;

@RestController
public class WorkTypeController {
	
	@Autowired
	private WorkTypeServices services;
	
	@RequestMapping(value = "/api/worktypes", 
					method = RequestMethod.GET)
	public Set<WorkType> getAllWorkTypes() {
		Set<WorkType> workTypes = services.getAllWorkTypes();
		return workTypes;
	}
	
	@RequestMapping(value = "/api/worktypes/{id}", 
					method = RequestMethod.GET)
	public WorkType getWorkType(@PathVariable("id") Integer id) {
		return services.getWorkType(id);
	}
	
	@RequestMapping(value = "/api/worktypes", 
					method = RequestMethod.POST)
	public WorkType addWorkType(@RequestBody WorkType workType) {
		return services.addWorkType(workType);
	}
	
	@RequestMapping(value = "/api/worktypes/{id}", 
					method = RequestMethod.PUT)
	public WorkType updateWorkType(@PathVariable("id") Integer id, @RequestBody WorkType workType) {
		workType.setId(id);
		return services.updateWorkType(workType);
	}
	
	@RequestMapping(value = "/api/worktypes/{id}", 
					method = RequestMethod.DELETE)
	public void deleteWorkType(@PathVariable("id") Integer id) {
		services.deleteWorkType(id);
	}
}
