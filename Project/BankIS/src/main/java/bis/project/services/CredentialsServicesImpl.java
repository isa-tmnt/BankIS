package bis.project.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import bis.project.repositories.UserRepository;
import bis.project.security.Credentials;
import bis.project.security.Permission;
import bis.project.security.Role;
import bis.project.security.User;

@Service
public class CredentialsServicesImpl implements CredentialsServices {
	
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public Credentials login(Credentials credentials) {
		User user = repository.findByEmail(credentials.getEmail());
		
		if(user != null) {
			byte[] salt = Base64Utils.decodeFromString(user.getSalt());
			byte[] expectedHash = Base64Utils.decodeFromString(user.getPassword());
			
			if(this.isExpectedPassword(credentials.getPassword().toCharArray(), salt, expectedHash)) {
				return credentials;
			}
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
		
		if(tokenizer.countTokens() <= 1) {
			return false;
		}
		
		String email = tokenizer.nextToken();
		String password = tokenizer.nextToken();
		
		User user = repository.findByEmail(email);
		
		if(user == null) return false;
		
		byte[] salt = Base64Utils.decodeFromString(user.getSalt());
		byte[] expectedHash = Base64Utils.decodeFromString(user.getPassword());
		
		if(!this.isExpectedPassword(password.toCharArray(), salt, expectedHash)) {
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

	@Override
	public byte[] hashPassword(char[] password, byte[] salt) {
		//keyLength = 256 (or more)
		//iterations = 10000
		try {
           SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
           PBEKeySpec spec = new PBEKeySpec( password, salt, ITERATIONS, KEY_LENGTH );
           SecretKey key = skf.generateSecret( spec );
           byte[] res = key.getEncoded( );
           
           return res;
       } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
           throw new RuntimeException( e );
       }
	}

	@Override
	public boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
		byte[] pwdHash = this.hashPassword(password, salt);
	    Arrays.fill(password, Character.MIN_VALUE);
	    
	    if (pwdHash.length != expectedHash.length) return false;
	    
	    for (int i = 0; i < pwdHash.length; i++) {
	    	if (pwdHash[i] != expectedHash[i]) return false;
	    }
	    
	    return true;
	}
}
