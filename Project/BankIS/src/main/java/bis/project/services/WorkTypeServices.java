package bis.project.services;

import java.util.Set;

import bis.project.model.WorkType;

public interface WorkTypeServices {
	
	public Set<WorkType> getAllWorkTypes();
	
	public WorkType getWorkType(Integer id);
	
	public WorkType addWorkType(WorkType workType);
	
	public WorkType updateWorkType(WorkType workType);

	public void deleteWorkType(Integer id);
}
