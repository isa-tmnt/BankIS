package bis.project.services;

import bis.project.security.Credentials;

public interface CredentialsServices {
	
	public Credentials login(Credentials credentials);
	
	public boolean isAuthorized(String authToken, String permission);
	
	public byte[] hashPassword(char[] password, byte[] salt);
	
	public boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash);
}
