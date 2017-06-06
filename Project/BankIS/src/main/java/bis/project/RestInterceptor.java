package bis.project;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bis.project.helpers.LogedUserGetter;
import bis.project.model.Log;
import bis.project.repositories.LogRepository;

public class RestInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LogManager.getLogger(); 
	
	@Autowired
	private LogRepository logRepository;
	
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
    throws Exception {
    	String email = LogedUserGetter.getEmail(request);
    	String logMessage;
    	if(email != null){
			logMessage = "User " + email + " gets response to " + request.getMethod() + " " + request.getRequestURI() + " with status " + response.getStatus();						
		}else{
			logMessage = "Not logged user requested gets response to " + request.getMethod() + " " + request.getRequestURI() + " with status" + response.getStatus();
		}
    	
        Log log = new Log();
	    log.setLogerUser(email);
	    log.setExceptionMessage(exception != null? exception.getMessage() : "");
	    log.setMethodType(request.getMethod());
	    log.setResponseStatus(response.getStatus());
	    log.setRequestPath(request.getRequestURI());
	    log.setTime(new Date());	 	 
    	
    	logger.info(logMessage);
    	logRepository.save(log);
    	
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	String email = LogedUserGetter.getEmail(request);
    	String logMessage;
    	if(email != null){
			logMessage = "User " + email + " requested " + request.getMethod() +  " " + request.getRequestURI();	
		}else{
			logMessage = "Not logged user requested " + request.getMethod() +  " " + request.getRequestURI();
		}
    	
    	Log log = new Log();
 	    log.setLogerUser(email);
 	    log.setMethodType(request.getMethod());
 	    log.setResponseStatus(response.getStatus());
 	    log.setTime(new Date());	 
 	    log.setRequestPath(request.getRequestURI());
    	 
 	    
    	logger.info(logMessage);
    	logRepository.save(log);
    	
    	return true;

    }


}