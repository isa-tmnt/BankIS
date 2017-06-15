package bis.project.services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import bis.project.repositories.UserRepository;
import bis.project.security.Credentials;
import bis.project.security.Permission;
import bis.project.security.Role;
import bis.project.security.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class CredentialsServicesImpl implements CredentialsServices {
	
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;
	
	private static final String ISSUER = "BankIS";
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public User login(Credentials credentials) {
		User user = repository.findByEmail(credentials.getEmail());
		
		if(user != null) {
			byte[] salt = Base64Utils.decodeFromString(user.getSalt());
			byte[] expectedHash = Base64Utils.decodeFromString(user.getPassword());
			
			if(this.isExpectedPassword(credentials.getPassword().toCharArray(), salt, expectedHash)) {
				return user;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean isJWTAuthorized(String jwt, String csrfToken, String authEmail, String permission) {
		
		if(jwt.equals("null")) return false;
		else if(csrfToken.equals("null")) return false;
		else if(authEmail.equals("null")) return false;
		
		User user = repository.findByEmail(authEmail);
		
		if(user != null) {
			try {
				Claims claims = this.parseJWT(jwt, user.getSalt());
				
				if(!claims.getSubject().equals(authEmail) || !claims.get("CSRF-TOKEN").equals(csrfToken)) {
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
			} catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		
		return false;
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
	
	@Override
	public String createJWT(String subject, String csrfToken, String secret) {
		
		String id = UUID.randomUUID().toString();
		
		//The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
		
		long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    
	    Calendar expDate = Calendar.getInstance();
		expDate.set(Calendar.HOUR_OF_DAY, expDate.get(Calendar.HOUR_OF_DAY) + 3);
		
		long ttlMillis = expDate.getTimeInMillis();
		Date exp = new Date(ttlMillis);
	    
	    //We will sign our JWT with our ApiKey secret
	    byte[] apiKeySecretBytes = Base64Utils.decodeFromString(secret);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId(id)
	                                .setIssuedAt(now)
	                                .setSubject(subject)
	                                .setIssuer(ISSUER)
	                                .claim("CSRF-TOKEN", csrfToken)
	                                .setExpiration(exp)
	                                .signWith(signatureAlgorithm, signingKey);
	 
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}

	@Override
	public Claims parseJWT(String jwt, String secret) {
		
		//This line will throw an exception if it is not a signed JWS (as expected)
	    Claims claims = Jwts.parser()         
	       .setSigningKey(Base64Utils.decodeFromString(secret))
	       .parseClaimsJws(jwt).getBody();
	    
	    return claims;
	}
}
