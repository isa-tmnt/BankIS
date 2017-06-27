package cagen.project.services;

import cagen.project.security.Credentials;
import cagen.project.security.User;
import io.jsonwebtoken.Claims;

public interface CredentialsServices {
	
	public User login(Credentials credentials);
	
	public byte[] hashPassword(char[] password, byte[] salt);
	
	public boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash);
	
	public String createJWT(String subject, String csrfToken, String secret);
	
	public Claims parseJWT(String jwt, String secret);
	
	public boolean isJWTAuthorized(String jwt, String csrfToken, String authEmail, String permission);
}
