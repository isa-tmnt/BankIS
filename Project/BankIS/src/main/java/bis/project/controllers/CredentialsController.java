package bis.project.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.security.Credentials;
import bis.project.security.User;
import bis.project.security.UserResponse;
import bis.project.services.CredentialsServices;

@RestController
public class CredentialsController {
	
	private static HashMap<String,IpMapping> loginCountMap = new HashMap<String,IpMapping>();
	
	@Autowired
	private CredentialsServices services;
	
	@RequestMapping(value = "/api/login", 
					method = RequestMethod.POST)
	public ResponseEntity<UserResponse> login(@RequestBody Credentials credentials, HttpServletRequest request) {
		
		IpMapping mapping = loginCountMap.get(request.getRemoteAddr());
		if(mapping != null && mapping.tryCount > 2){
			long diff = Math.abs(new Date().getTime() - mapping.lastTryTime.getTime());
			double diffMins = (double) (diff * 1.0/ (60 * 1000));
			if(diffMins < 5){
				//return 3 times failure error
				return new ResponseEntity<UserResponse>(HttpStatus.LOCKED);
			}else{
				mapping.tryCount = 0;
			}
		}
		
		User user = services.login(credentials);
		
		if(user != null) {
			UserResponse response = new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getBank());
			
			String csrfToken = UUID.randomUUID().toString();
			String jwt = services.createJWT(user.getEmail(), csrfToken, user.getBank().getId(), user.getSalt());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(HttpHeaders.SET_COOKIE, "jwt=" + jwt + "; Secure; HttpOnly");
			responseHeaders.set("CsrfToken", csrfToken);
			
			return new ResponseEntity<UserResponse>(response, responseHeaders, HttpStatus.OK);
		}
		
		
		mapping = loginCountMap.get(request.getRemoteAddr());
		if(mapping == null){
			IpMapping mm = new IpMapping();
			mm.lastTryTime = new Date();
			mm.tryCount = 1;
			loginCountMap.put(request.getRemoteAddr(), mm);
		}else{
			mapping.lastTryTime = new Date();
			mapping.tryCount++;
		}
		
		return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/api/logout", 
					method = RequestMethod.GET)
	public ResponseEntity<String> logout() {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.SET_COOKIE, "jwt=null; Secure; HttpOnly");
		
		return new ResponseEntity<String>("Logout", responseHeaders, HttpStatus.OK);
	}
	
	private class IpMapping{
		public Date lastTryTime;
		public int tryCount;
	}
}
