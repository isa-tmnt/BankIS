package bis.project.helpers;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.WebRequest;

public class LogedUserGetter {
	
	public static String getEmail(WebRequest req) {
		String email = req.getHeader("AuthEmail");
		
		if(email != null && email != "" && email != "null") {
			return email;
		}
		
		/*String authHeader = req.getHeader("Authorization");
		if(authHeader.indexOf("Basic ") > -1){
			String userBase64 = authHeader.substring(6, authHeader.length());
			String user = new String(Base64.getDecoder().decode(userBase64));
			String[] subs = user.split(":");
			if(subs.length == 2){
				String email = subs[0];
				return email;
			}
		}*/
		
		return null;
	}
	
	public static String getEmail(HttpServletRequest req) {
		String email = req.getHeader("AuthEmail");
		
		if(email != null && email != "" && email != "null") {
			return email;
		}
		
		/*String authHeader = req.getHeader("Authorization");
		if(authHeader != null && authHeader.indexOf("Basic ") > -1){
			String userBase64 = authHeader.substring(6, authHeader.length());
			String user = new String(Base64.getDecoder().decode(userBase64));
			String[] subs = user.split(":");
			if(subs.length == 2){
				String email = subs[0];
				return email;
			}
		}*/
		
		return null;
	}
}
