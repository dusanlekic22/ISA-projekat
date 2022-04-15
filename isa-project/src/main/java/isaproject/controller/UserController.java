package isaproject.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.BusinessOwnerRegistrationRequestDTO;
import isaproject.dto.RoleDTO;
import isaproject.dto.UserDTO;
import isaproject.mapper.RequestMapper;
import isaproject.mapper.UserMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.service.UserService;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping()
	@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'FISHING_TRAINER', 'COTTAGE_OWNER')")
	public UserDTO user(Principal user) {
		return UserMapper.UserToDTO(this.userService.findByUsername(user.getName()));
	}
	
	@PostMapping("/authorize")
	@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'FISHING_TRAINER', 'COTTAGE_OWNER')")
	public boolean authorize(@RequestBody Set<RoleDTO> roles, Principal user) {
		return this.userService.isUserAuthorized(roles, user.getName());
	}
	
	@PostMapping("/acceptUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BusinessOwnerRegistrationRequestDTO> acceptUser(@RequestBody BusinessOwnerRegistrationRequestDTO registrationRequestDTO) {
		BusinessOwnerRegistrationRequest registrationRequest = userService.activateUser(registrationRequestDTO);
		BusinessOwnerRegistrationRequestDTO requestDTO = RequestMapper.BusinessOwnerRegistrationRequesToDTO(registrationRequest);
		return new ResponseEntity<BusinessOwnerRegistrationRequestDTO>(requestDTO, HttpStatus.OK);
	}
	
	@PostMapping("/declineUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BusinessOwnerRegistrationRequestDTO> declineUser(@RequestBody BusinessOwnerRegistrationRequestDTO registrationRequestDTO) {
		BusinessOwnerRegistrationRequest registrationRequest = userService.declineUser(registrationRequestDTO);
		BusinessOwnerRegistrationRequestDTO requestDTO = RequestMapper.BusinessOwnerRegistrationRequesToDTO(registrationRequest);
		return new ResponseEntity<BusinessOwnerRegistrationRequestDTO>(requestDTO, HttpStatus.OK);
	}
	
	@GetMapping("/registrationRequests")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<BusinessOwnerRegistrationRequestDTO>> getBusinessOwnerRegistrationRequests() {
		List<BusinessOwnerRegistrationRequestDTO> registrationRequests = userService.getAllRegistrationRequests();
		return new ResponseEntity<List<BusinessOwnerRegistrationRequestDTO>>(registrationRequests, HttpStatus.OK);
	}
	
}
