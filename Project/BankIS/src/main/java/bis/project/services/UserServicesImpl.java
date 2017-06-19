package bis.project.services;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import bis.project.model.Bank;
import bis.project.repositories.BankRepository;
import bis.project.repositories.UserRepository;
import bis.project.security.PasswordDTO;
import bis.project.security.User;
import bis.project.security.UserDTO;
import bis.project.security.UserResponse;

@Service
public class UserServicesImpl implements UserServices {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private CredentialsServices services;
	
	@Autowired
	private BankRepository bRepository;
	
	@Override
	public Set<UserResponse> getAllUsers(Integer bankId) {
		Set<UserResponse> users = new HashSet<UserResponse>();
		for(User user : repository.findAll()) {
			if(user.getBank().getId() == bankId) {
				UserResponse userDTO = new UserResponse(user.getFirstName(), user.getLastName(), 
			      		  		  user.getEmail(), user.getBank());
				userDTO.setRoles(user.getRoles());
				
				users.add(userDTO);
			}
		}
		
		return users;
	}

	/*@Override
	public UserDTO getUser(Integer id) {
		User user = repository.findOne(id);
		
		if(user != null) {
			UserDTO userDTO = new UserDTO(user.getFirstName(), user.getLastName(), 
				      		  user.getEmail(), user.getPassword(), user.getBank());
			userDTO.setRoles(user.getRoles());
			
			return userDTO;
		}
		
		return null;
	}*/

	@Override
	public UserResponse getUserByEmail(String email, Integer bankId) {
		User user = repository.findByEmail(email);
		
		if(user != null && user.getBank().getId() == bankId) {
			UserResponse userDTO = new UserResponse(user.getFirstName(), user.getLastName(), 
						      user.getEmail(), user.getBank());
			userDTO.setRoles(user.getRoles());
			
			return userDTO;
		}
		
		return null;
	}

	@Override
	public UserResponse addUser(UserDTO userDTO, Integer bankId) {
		User u = repository.findByEmail(userDTO.getEmail());
		
		if(u != null) return null;
		
		Bank bank = bRepository.findOne(bankId);
		
		if(bank == null) return null;
		
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setBank(bank);
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
			UserResponse response = new UserResponse(newUser.getFirstName(), newUser.getLastName(), 
													 newUser.getEmail(), newUser.getBank());
			response.setRoles(newUser.getRoles());
			
			return response;
		}
		
		return null;
	}

	@Override
	public UserResponse updateUser(String email, PasswordDTO dto) {
		User existingUser = repository.findByEmail(email);
		
		if(existingUser != null) {
			byte[] salt = Base64Utils.decodeFromString(existingUser.getSalt());
			byte[] expectedHash = Base64Utils.decodeFromString(existingUser.getPassword());
			
			if(services.isExpectedPassword(dto.getOldPassword().toCharArray(), salt, expectedHash)) {
				if(dto.getNewPassword().equals(dto.getRepeatedPassword())) {
					byte[] newHashPassword = services.hashPassword(dto.getNewPassword().toCharArray(), salt);
					String newPassword = Base64Utils.encodeToString(newHashPassword);
					
					existingUser.setPassword(newPassword);
					User updatedUser = repository.save(existingUser);
					
					if(updatedUser != null) {
						UserResponse response = new UserResponse(updatedUser.getFirstName(), updatedUser.getLastName(), 
								updatedUser.getEmail(), updatedUser.getBank());
						response.setRoles(updatedUser.getRoles());
						
						return response;
					}
				}
				
			}
		}
		
		return null;
	}

	@Override
	public void deleteUser(String email, Integer bankId) {
		User user = repository.findByEmail(email);
		
		if(user != null && user.getBank().getId() == bankId) {
			repository.delete(user.getId());
		}
	}
}
