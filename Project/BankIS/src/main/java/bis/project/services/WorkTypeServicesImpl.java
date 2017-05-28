package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.WorkType;
import bis.project.repositories.WorkTypeRepository;

@Service
public class WorkTypeServicesImpl implements WorkTypeServices {

	@Autowired
	private WorkTypeRepository repository;
	
	@Override
	public Set<WorkType> getAllWorkTypes() {
		Set<WorkType> workTypes = new HashSet<WorkType>();
		for(WorkType workType : repository.findAll()) {
			workTypes.add(workType);
		}
		
		return workTypes;
	}

	@Override
	public WorkType getWorkType(Integer id) {
		WorkType workType = repository.findOne(id);
		
		if(workType != null) {
			return workType;
		}
		
		return null;
	}

	@Override
	public WorkType addWorkType(WorkType workType) {
		return repository.save(workType);
	}

	@Override
	public WorkType updateWorkType(WorkType workType) {
		return repository.save(workType);
	}

	@Override
	public void deleteWorkType(Integer id) {
		WorkType workType = repository.findOne(id);
		
		if(workType != null) {
			repository.delete(id);
		}
	}
}
