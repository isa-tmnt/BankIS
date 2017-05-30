package bis.project.validators;

import bis.project.model.WorkType;

public class WorkTypeValidator {
	public static void Validate(WorkType workType) throws ValidationException{
		
		ValidationException.ThrowIfNullOrEmpty(workType.getCode(), "Code");
		ValidationException.ThrowIfNullOrEmpty(workType.getName(), "Name");
		
		ValidationException.ThrowIfLengthGratherThan(60, workType.getName(), "Name");
	}
}
