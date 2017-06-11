package bis.project.services;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import bis.project.repositories.UserRepository;
import bis.project.security.User;
import bis.project.security.UserDTO;

@Service
public class UserServicesImpl implements UserServices {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private CredentialsServices services;
	
	@Override
	public Set<UserDTO> getAllUsers() {
		Set<UserDTO> users = new HashSet<UserDTO>();
		for(User user : repository.findAll()) {
			UserDTO userDTO = new UserDTO(user.getFirstName(), user.getLastName(), 
		      		  		  user.getEmail(), user.getPassword(), user.getBank());
			userDTO.setRoles(user.getRoles());
			
			users.add(userDTO);
		}
		
		return users;
	}

	@Override
	public UserDTO getUser(Integer id) {
		User user = repository.findOne(id);
		
		if(user != null) {
			UserDTO userDTO = new UserDTO(user.getFirstName(), user.getLastName(), 
				      		  user.getEmail(), user.getPassword(), user.getBank());
			userDTO.setRoles(user.getRoles());
			
			return userDTO;
		}
		
		return null;
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		User user = repository.findByEmail(email);
		
		if(user != null) {
			UserDTO userDTO = new UserDTO(user.getFirstName(), user.getLastName(), 
						      user.getEmail(), user.getPassword(), user.getBank());
			userDTO.setRoles(user.getRoles());
			
			return userDTO;
		}
		
		return null;
	}

	@Override
	public UserDTO addUser(UserDTO userDTO) {
		User u = repository.findByEmail(userDTO.getEmail());
		
		if(u != null) return null;
		
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setBank(userDTO.getBank());
		user.setRoles(userDTO.getRoles());
		
		SecureRandom random = new SecureRandom();
		
		byte[] salt = new byte[32];
		random.nextBytes(salt);
		
		byte[] hashPassword = services.hashPassword(userDTO.getPassword().toCharArray(), salt);
		String password = Base64Utils.encodeToString(hashPassword);
		String sSalt = Base64Utils.encodeToString(salt);
		
		user.setSalt(sSalt);
		user.setPassword(password);
		
		User newUser = repository.save(user);
		
		if(newUser != null) {
			userDTO.setPassword(newUser.getPassword());
			return userDTO;
		}
		
		return null;
	}

	@Override
	public UserDTO updateUser(String email, UserDTO userDTO) {
		User existingUser = repository.findByEmail(email);
		
		if(existingUser != null) {
			User user = new User();
			user.setId(existingUser.getId());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			user.setBank(userDTO.getBank());
			user.setRoles(userDTO.getRoles());
			user.setSalt(existingUser.getSalt());
			
			byte[] salt = Base64Utils.decodeFromString(user.getSalt());
			byte[] expectedHash = Base64Utils.decodeFromString(existingUser.getPassword());
			
			if(services.isExpectedPassword(userDTO.getPassword().toCharArray(), salt, expectedHash)) {
				user.setPassword(existingUser.getPassword());
			} else {
				byte[] hashPassword = services.hashPassword(userDTO.getPassword().toCharArray(), salt);
				String password = Base64Utils.encodeToString(hashPassword);
				user.setPassword(password);
			}
			
			User newUser = repository.save(user);
			
			if(newUser != null) {
				userDTO.setPassword(newUser.getPassword());
				return userDTO;
			} else  {
				return null;
			}
		}
		
		return null;
	}

	@Override
	public void deleteUser(String email) {
		User user = repository.findByEmail(email);
		
		if(user != null) {
			repository.delete(user.getId());
		}
	}
}
