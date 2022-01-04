package isaproject.mapper;

import isaproject.dto.UserDTO;
import isaproject.model.User;

public class UserMapper {

	public UserMapper() {}
	
	public User UsertoUserDTO(UserDTO userDTO) {
	User user = new User();
	user.setUsername(userDTO.getUsername());
	user.setPassword(userDTO.getPassword());
	user.setFirstName(userDTO.getFirstName());
	user.setLastName(userDTO.getLastName());
	user.setEmail(userDTO.getEmail());
	user.setVerificationCode(userDTO.getVerificationCode());
	user.setRoles(userDTO.getRoles());
	user.setPhoneNumber(userDTO.getPhoneNumber());
	user.setAddress(userDTO.getAddress());
	user.setLastPasswordResetDate(userDTO.getLastPasswordResetDate());
	return user;
	}
}
