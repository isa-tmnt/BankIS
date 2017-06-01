package bis.project.services;

import java.util.StringTokenizer;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.security.Credentials;
import bis.project.security.Permission;
import bis.project.security.Role;
import bis.project.security.User;

@Service
public class CredentialsServicesImpl implements CredentialsServices {
	
	@Autowired
	private UserServices services;
	
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	
	@Override
	public Credentials login(Credentials credentials) {
		User user = services.getUserByEmail(credentials.getEmail());
		
		if(user != null && user.getPassword().equals(credentials.getPassword())) {
			return credentials;
		}
		
		return null;
	}

	@Override
	public boolean isAuthorized(String authToken, String permission) {
		
		if(authToken == "" || authToken == null) {
			return false;
		}
		
		authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
		String decodedString = Base64.decodeAsString(authToken);
		StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
		String email = tokenizer.nextToken();
		String password = tokenizer.nextToken();
		
		User user = services.getUserByEmail(email);
		
		if(user == null || !user.getPassword().equals(password)) {
			return false;
		}
		
		for(Role role : user.getRoles()) {
			for(Permission permissionn : role.getPermissions()) {
				if(permission.toLowerCase().equals(permissionn.getName().toLowerCase())) {
					return true;
				}
			}
		}
		
		return false;
	}
}
