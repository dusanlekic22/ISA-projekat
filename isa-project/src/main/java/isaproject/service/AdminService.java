package isaproject.service;

import isaproject.dto.UserChangePasswordDTO;
import isaproject.dto.UserDTO;
import isaproject.model.Admin;

public interface AdminService {
	
	Admin registerAdmin(UserDTO userDTO);
	void changePassword(UserChangePasswordDTO adminDTO);
	Admin findByUsername(String username);
	
}
