package bis.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.security.Credentials;
import bis.project.security.User;

@Service
public class CredentialsServicesImpl implements CredentialsServices {
	
	@Autowired
	private UserServices services;
	
	@Override
	public Credentials login(Credentials credentials) {
		User user = services.getUserByEmail(credentials.getEmail());
		
		if(user != null && user.getPassword().equals(credentials.getPassword())) {
			return credentials;
		}
		
		return null;
	}
}
