package bis.project.services;

import bis.project.security.Credentials;
import bis.project.security.User;
import io.jsonwebtoken.Claims;

public interface CredentialsServices {
	
	public User login(Credentials credentials);
	
	public boolean isAuthorized(String authToken, String permission);
	
	public byte[] hashPassword(char[] password, byte[] salt);
	
	public boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash);
	
	public String createJWT(String subject, String csrfToken, Integer bankId, String secret);
	
	public Claims parseJWT(String jwt, String secret);
	
	public boolean isJWTAuthorized(String jwt, String csrfToken, String authEmail, Integer bankId, String permission);
}
