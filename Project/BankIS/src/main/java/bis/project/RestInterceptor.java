package bis.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bis.project.helpers.LogedUserGetter;

public class RestInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LogManager.getLogger(); 
	
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
    	
    	logger.info(logMessage);
    	
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
    	
    	logger.info(logMessage);
    	
    	return true;

    }


}