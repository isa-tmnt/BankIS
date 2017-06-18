package bis.project.services;

import java.util.Set;

import bis.project.security.PasswordDTO;
import bis.project.security.UserDTO;
import bis.project.security.UserResponse;

public interface UserServices {
	
	public Set<UserResponse> getAllUsers();
	
	//public UserResponse getUser(Integer id);
	
	public UserResponse getUserByEmail(String email);
	
	public UserResponse addUser(UserDTO userDTO);
	
	public UserResponse updateUser(String email, PasswordDTO dto);
	
	public void deleteUser(String email);
}
