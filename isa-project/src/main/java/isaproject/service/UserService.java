package isaproject.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import isaproject.dto.BusinessOwnerRegistrationRequestDTO;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.User;

public interface UserService extends UserDetailsService {

	User findByUsername(String username);
	BusinessOwnerRegistrationRequest activateUser(BusinessOwnerRegistrationRequestDTO registrationRequestDTO);
	BusinessOwnerRegistrationRequest declineUser(BusinessOwnerRegistrationRequestDTO registrationRequestDTO);
	List<BusinessOwnerRegistrationRequestDTO> getAllRegistrationRequests();
}