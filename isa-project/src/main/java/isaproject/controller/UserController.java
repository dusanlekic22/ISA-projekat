package isaproject.controller;

import java.security.Principal;
import java.util.List;

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
import isaproject.mapper.RequestMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.User;
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
	
	@GetMapping("/lol")
	@PreAuthorize("hasRole('USER')")
	public User user(Principal user) {
		return this.userService.findByUsername(user.getName());
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
