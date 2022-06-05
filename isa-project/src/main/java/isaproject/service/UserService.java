package isaproject.service;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import isaproject.dto.BusinessOwnerRegistrationRequestDTO;
import isaproject.dto.RoleDTO;
import isaproject.dto.UserDTO;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.User;

public interface UserService extends UserDetailsService {
	
	User findById(Long userId);
	User findByUsername(String username);
	UserDTO updateUser(Long id, UserDTO user,Principal loggedInUser);
	BusinessOwnerRegistrationRequest activateUser(BusinessOwnerRegistrationRequestDTO registrationRequestDTO);
	BusinessOwnerRegistrationRequest declineUser(BusinessOwnerRegistrationRequestDTO registrationRequestDTO);
	List<BusinessOwnerRegistrationRequestDTO> getAllRegistrationRequests();
	boolean isUserAuthorized(Set<RoleDTO> roles, String username);
	Set<UserDTO> findAll();
	UserDTO deleteById(Long userId);
}