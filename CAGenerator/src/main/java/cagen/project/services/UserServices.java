package cagen.project.services;

import java.util.Set;

import cagen.project.security.UserDTO;
import cagen.project.security.UserResponse;

public interface UserServices {
	
	public Set<UserResponse> getAllUsers();
	
	//public UserResponse getUser(Integer id);
	
	public UserResponse getUserByEmail(String email);
	
	public UserResponse addUser(UserDTO userDTO);
	
	//public UserResponse updateUser(String email, PasswordDTO dto);
	
	public void deleteUser(String email);
}
