package bis.project.services;

import java.util.Set;

import bis.project.security.UserDTO;

public interface UserServices {
	
	public Set<UserDTO> getAllUsers();
	
	public UserDTO getUser(Integer id);
	
	public UserDTO getUserByEmail(String email);
	
	public UserDTO addUser(UserDTO userDTO);
	
	public UserDTO updateUser(String email, UserDTO userDTO);
	
	public void deleteUser(String email);
}
