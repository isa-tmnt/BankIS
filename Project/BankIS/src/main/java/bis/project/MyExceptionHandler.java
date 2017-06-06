package bis.project;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import bis.project.helpers.LogedUserGetter;
import bis.project.model.Log;
import bis.project.repositories.LogRepository;
import bis.project.validators.ValidationException;
import bis.project.validators.ValidationFailedReport;

@RestControllerAdvice 
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
 
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private LogRepository logRepository;
	
	@ExceptionHandler(value = { ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationFailedReport ValidationExceptionHandler(Exception ex, WebRequest req) {
		
		String email = LogedUserGetter.getEmail(req);
		String logMessage;
		
	
		if(email != null){
			logMessage = "User " + email + " caused ValidationException, cause: " + ex.getMessage();						
		}else{
			logMessage = "Not logged user caused ValidationException, cause: " + ex.getMessage();
		}
	    logger.info(logMessage);
	    
	    Log log = new Log();
	    log.setLogerUser(email);
	    log.setExceptionMessage(ex != null? ex.getMessage() : "");
	    log.setAbout("User caused ValidationException");	
	    log.setTime(new Date());
	    
	    logRepository.save(log);
	    
        return new ValidationFailedReport(ex.getMessage());
    }
	

	@ExceptionHandler(value = { org.springframework.dao.DataIntegrityViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SqlViolation fgddfgf(Exception ex, WebRequest req) {

		String email = LogedUserGetter.getEmail(req);
		
	    logger.info(ex.getMessage());
	    
	    Log log = new Log();
	    log.setLogerUser(email);
	    log.setExceptionMessage(ex != null? ex.getMessage() : "");
	    log.setAbout("DataIntegrityViolationException thrown");	 	    
	    log.setTime(new Date());
	    
	    logRepository.save(log);
	    
        return new SqlViolation("Data is possiably linked to other data, so we can't permit this action"); 
        		
    }
	
	
	
	
	public class SqlViolation{
		public String cause;
		public SqlViolation(String cause){
			this.cause = cause;
		}
	}
}