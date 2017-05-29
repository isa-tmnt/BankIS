package bis.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import bis.project.validators.ValidationException;
import bis.project.validators.ValidationFailedReport;

@RestControllerAdvice 
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
 
	@ExceptionHandler(value = { ValidationException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ValidationFailedReport unknownException(Exception ex, WebRequest req) {
        return new ValidationFailedReport(ex.getMessage());
    }
}